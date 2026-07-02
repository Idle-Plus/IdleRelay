package dev.uraxys.idleclient.tools.test

import kotlin.math.min
import kotlin.random.Random

fun main() {
	// --- Parameters ---
	val pHit = 0.5627//0.667
	//val pHit = 0.662
	val pDodge = 0.2178//0.1638
	//val pDodge = 0.1638
	val pTake = pHit * (1.0 - pDodge)
	val pNoDamage = 1.0 - pTake

	val dmgMin = 1
	val dmgMax = 48//42
	val food = 24//17
	val maxHp = 120//97
	println("Calculating...")

	// --- Helper function ---
	fun nextHpAfterDamage(hp: Int, dmg: Int): Int {
		return min(maxHp, hp - dmg + food)
	}

	val n = maxHp
	val A = Array(n) { DoubleArray(n) { 0.0 } }
	val b = DoubleArray(n) { 1.0 } // right-hand side is 1 for every equation

	// Build the linear system: E(h) - Σ p(h→h') E(h') = 1
	for (h in 1..maxHp) {
		val i = h - 1
		A[i][i] = 1.0

		// Transition for no damage (enemy misses or dodged)
		val hNo = min(maxHp, h + food)
		val jNo = hNo - 1
		A[i][jNo] -= pNoDamage

		// Transitions for all possible damage amounts
		val perDmgProb = pTake / (dmgMax - dmgMin + 1)
		for (dmg in dmgMin..dmgMax) {
			if (dmg >= h) {
				// Death transition — contributes nothing to future expectation
				continue
			}
			val hAfter = nextHpAfterDamage(h, dmg)
			val j = hAfter - 1
			A[i][j] -= perDmgProb
		}
	}

	// --- Solve A * x = b using Gaussian elimination ---
	val E = gaussianSolve(A, b)

	println("Expected turns to death from full HP (97): %.3f".format(E[maxHp - 1]))
	println("Expected turns with simulation runs: ${simulateBattle(
		maxHit = dmgMax,
		healAmount = food,
		health = maxHp,
		hitChance = pHit,
		dodgeChance = pDodge,
		simulations = 1
	)}")
	println("Expected turns from first few HP values:")
	for (h in 1..E.size) {
		//println("HP $h → %.3f turns".format(E[h - 1]))
	}
}

fun gaussianSolve(a: Array<DoubleArray>, b: DoubleArray): DoubleArray {
	val n = b.size
	val A = Array(n) { a[it].clone() }
	val B = b.clone()

	for (i in 0 until n) {
		// Partial pivoting
		var maxRow = i
		for (k in i + 1 until n) {
			if (kotlin.math.abs(A[k][i]) > kotlin.math.abs(A[maxRow][i])) {
				maxRow = k
			}
		}
		val tmpRow = A[i]
		A[i] = A[maxRow]
		A[maxRow] = tmpRow
		val tmpVal = B[i]
		B[i] = B[maxRow]
		B[maxRow] = tmpVal

		// Normalize pivot row
		val pivot = A[i][i]
		for (j in i until n) A[i][j] /= pivot
		B[i] /= pivot

		// Eliminate below
		for (k in i + 1 until n) {
			val factor = A[k][i]
			for (j in i until n) {
				A[k][j] -= factor * A[i][j]
			}
			B[k] -= factor * B[i]
		}
	}

	// Back substitution
	val x = DoubleArray(n)
	for (i in n - 1 downTo 0) {
		var sum = B[i]
		for (j in i + 1 until n) sum -= A[i][j] * x[j]
		x[i] = sum
	}
	return x
}

fun simulateBattle(
	health: Int = 97,
	healAmount: Int = 17,
	maxHit: Int = 42,
	hitChance: Double = 0.667,
	dodgeChance: Double = 0.1638,
	simulations: Int = 1_000_000
): Double {
	var totalTicks = 0.0
	repeat(simulations) {
		var hp = health
		var ticks = 0
		while (true) {
			ticks++

			// Dodge check
			val dodged = Random.nextDouble() < dodgeChance
			if (!dodged) {
				// Enemy hit check
				val hit = Random.nextDouble() < hitChance
				if (hit) {
					val damage = Random.nextInt(1, maxHit + 1)
					hp -= damage
					if (hp <= 0) break // death
				}
			}

			// Eat food (heal) unless dead
			hp = (hp + healAmount).coerceAtMost(health)
		}
		totalTicks += ticks
	}
	return totalTicks / simulations
}

fun getExpectedAttacksToKill(health: Int, minHit: Int, maxHit: Int, hitChance: Double): Double {
	val damageRange = maxHit - minHit + 1
	val damageProbability = hitChance / damageRange

	val expected = DoubleArray(health + 1)
	val prefixExpected = DoubleArray(health + 2)
	expected[0] = 0.0

	for (currentHealth in 1..health) {

		// Update the "width" of the prefix sum window.
		val left = maxOf(currentHealth - maxHit, 0)
		val right = currentHealth - minHit

		var sum = 0.0
		if (right >= 0) {
			sum = if (left <= 0) prefixExpected[right]
			else prefixExpected[right] - prefixExpected[left - 1]
		}

		// Expected remaining attacks after a successful hit, averaged over
		// the damage range.
		val expectedAfterHit = damageProbability * sum

		// Calculate the expected attacks to reach the end from the current
		// health.
		// - one attack for the current action
		// - plus expected remaining attacks weighted by hit chance
		expected[currentHealth] = (1 + expectedAfterHit) / hitChance
		// Maintain prefix sum
		prefixExpected[currentHealth] = prefixExpected[currentHealth - 1] + expected[currentHealth]
	}

	return expected[health]
}