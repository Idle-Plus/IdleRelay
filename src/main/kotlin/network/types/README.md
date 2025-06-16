All the objects and packets sent by the server. This is needed to rehydrate
the default values omitted by the server. If not, we would need to handle this
on the frontend, which is way worse due to how TypeScript works.