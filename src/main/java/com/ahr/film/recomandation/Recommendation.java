package com.ahr.film.recomandation;

import com.ahr.film.entity.Film;
import com.ahr.film.entity.FilmScore;
import com.ahr.film.entity.User;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;

import java.sql.Connection;
import java.util.*;

public class Recommendation {

    public static List<Film> getRecommendFilmList(User user){
        List<Film> filmList = new ArrayList<>();
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>(); //存放推荐电影相关内容


        return filmList;
    }

    public static List<Film> recommend(User u){
        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);


        List<User> allUser = MySQLUtils.doQuery(conn, User.class);
        conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        List<Film> allFilm = MySQLUtils.doQuery(conn, Film.class);

        int[][] curMatrix = new int[allFilm.size() + 5][allFilm.size() + 5];
        int[][] comMatrix = new int[allFilm.size() + 5][allFilm.size() + 5];
        int[] N = new int[allFilm.size() + 5];

        for(User user : allUser){
            if(user.getUserId().equals(u.getUserId())) continue;

            conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
            FilmScore filmScore = new FilmScore();
            filmScore.setUserId(user.getUserId());
            List<FilmScore> filmScores = MySQLUtils.doQuery(conn, filmScore);


            for(int i = 0; i < allFilm.size(); i++){
                for(int j = 0; j < allFilm.size(); j++){
                    curMatrix[i][j] = 0;
                }
            }

            for(int i = 0; i < filmScores.size(); i++){
                int pid1 = filmScores.get(i).getFilmId();
                ++N[pid1];
                for(int j = i+1; j < filmScores.size(); j++){
                    int pid2 = filmScores.get(j).getFilmId();
                    ++curMatrix[pid1][pid2];
                    ++curMatrix[pid2][pid1]; //两两加一
                }
            }
            //累加所有矩阵, 得到共现矩阵
            for(int i = 0; i < allFilm.size(); i++){
                for(int j = 0; j < allFilm.size(); j++){
                    int pid1 = allFilm.get(i).getFilmId(), pid2 = allFilm.get(j).getFilmId();
                    comMatrix[pid1][pid2] += curMatrix[pid1][pid2];
                    comMatrix[pid1][pid2] += curMatrix[pid1][pid2];
                }
            }

        }
        TreeSet<Film> preList = new TreeSet<Film>(new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                if(o1.getW()!=o2.getW())
                    return (int) (o1.getW()-o2.getW())*100;
                else{
                    Connection c = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
                    List<Film> like1 = MySQLUtils.doQuery(c, o1);
                    c = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
                    List<Film> like2 = MySQLUtils.doQuery(c, o2);
                    return like1.size()-like2.size();
                }
            }
        });
        conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        FilmScore filmScore = new FilmScore();
        filmScore.setUserId(u.getUserId());
        List<FilmScore> filmScores = MySQLUtils.doQuery(conn, filmScore);
        boolean[] used = new boolean[allFilm.size()];
        for(FilmScore fs : filmScores){
            int Nij = 0;
            Double Wij;
            Film tmp;
            int i = fs.getFilmId();
            for(Film f : allFilm){
                if(fs.getFilmId().equals(f.getFilmId())){
                    continue;
                }
                int j = f.getFilmId();

                Nij = comMatrix[i][j];
                Wij = (double) Nij / Math.sqrt(N[i] * N[j]);
                conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
                Film f1 = new Film();
                f1.setFilmId(f.getFilmId());
                tmp = MySQLUtils.doQuery(conn, f1).get(0);
                tmp.setW(Wij);

                if(used[tmp.getFilmId()]) continue;
                preList.add(tmp);
                used[tmp.getFilmId()] = true;
            }
        }

        List<Film> ans = new ArrayList<>();
        for(int i = 0; i < 5 && preList.size() > 0; i++){
            ans.add(preList.pollLast());

        }
        return ans;
    }
}
