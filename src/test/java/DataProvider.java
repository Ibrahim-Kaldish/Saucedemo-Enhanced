public class DataProvider {
    @org.testng.annotations.DataProvider(name = "validCredentials")
    public Object[][] getValidCredentials(){
        return new Object[][] {
                {"standard_user","secret_sauce"},
//                {"visual_user","secret_sauce"},
//                {"error_user","secret_sauce"}
        };
    }

    @org.testng.annotations.DataProvider(name = "invalidCredentials")
    public Object[][] getInvalidCredentials(){
        return new Object[][] {
                {"locked_out_user","secret_sauce"},
        };
    }

    @org.testng.annotations.DataProvider (name = "getInfo")
    public Object[][] getInfo(){
        return new Object[][] {
                {"Ibrahim","Kaldish","13612"},
                {"Youssef","Ali","13612"},
                {"Yasmine","Ali","13612"}
        };
    }
}
