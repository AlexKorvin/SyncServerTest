package mykhailenko.aleks.com.serverlist.rest;

public class APIUtils {

    public static final String BASE_URL = "https://api.ivpn.net/";

    private APIUtils() {
    }

    public static IVPNApi getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(IVPNApi.class);
    }
}
