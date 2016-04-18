package com.alexcrijman.stsapp10.uitls;


public class ServerEndPointsAPI {

    private static final String BASE_ENDPOINT = "http://sts.sisc.ro:3000/";
    private static final String REGISTER = BASE_ENDPOINT + "register";
    private static final String AUTENTIFICATION = BASE_ENDPOINT + "autentification";
    private static final String LOAD_PlAYS = BASE_ENDPOINT + "loadplays";
    private static final String VOTE_FOR_WIN = BASE_ENDPOINT + "voteforwin";

    public static String getEndPointURL(String scope) {

        String resultURL = null;

        switch (scope) {
            case "register":
                return REGISTER;

            case "autentification":
                return AUTENTIFICATION;


            case "loadplays":
                return LOAD_PlAYS;

            case "voteforwin":
                return VOTE_FOR_WIN;
            default:
                resultURL = BASE_ENDPOINT;
        }
        return resultURL;

    }
}


