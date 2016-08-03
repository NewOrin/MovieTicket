package com.etc.movieticket.utils;

/**
 * Created by NewOrin Zhang on 2016/7/26.
 * E-mail: NewOrin@163.com
 */
public class Constants {
    public static final String SERVER_URL = "http://192.168.3.109:80/MovieTicket/";

    public static final String DO_LOGIN = "user/login";
    public static final String DO_REGISTER = "user/add";
    public static final String DO_GET_MOVIE = "movie/released?pagenum=1";
    public static final String DO_GET_MOVIE_INFO = "movie/selectinf?showid=";
    public static final String M_IMAX = "imax2d";
    public static final String M_IMAX_3D = "imax3d";
    public static final String M_3D = "m3d";
    public static final String M_2D = "no";

    public static final String MOVIE_ISRELEASED = "1";
    public static final String MOVIE_NOTRELEASED = "0";
}
