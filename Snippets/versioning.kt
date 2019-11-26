    @Version("1")
    @Get("/demo")
    fun demoV1(@RequestAttribute userId: String): String {
        return "Hello $userId"
    }

    @Version("2")
    @Get("/demo")
    fun demoV2(@RequestAttribute userId: String): String {
        return "Welcome $userId"
    }
