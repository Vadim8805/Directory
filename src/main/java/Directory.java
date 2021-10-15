import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Directory {
    private static final String url="jdbc:h2:/Users/a19596952/Desktop/Projects/Directory/DATABASE/Directory";
    private static final String user="svn";
    private static final String password="1234";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rsBeforeAdd;

    public static void main(String[] args) throws IOException {

        try {
            Class.forName("org.h2.Driver");

            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();

            rsBeforeAdd = stmt.executeQuery("SELECT id FROM DIRECTORY");
            List<Integer> excludedId = new ArrayList<>();
            while (rsBeforeAdd.next()){
                int id = rsBeforeAdd.getInt("ID");
                excludedId.add(id);
            }

            List<City> citiesFromFile = Util.parseDirectory(excludedId, "/Users/a19596952/Desktop/Projects/Directory/Directory.txt");
            List<String> stmtStr = Util.convertCityToStatementStr(citiesFromFile);

            for (String s: stmtStr) {
                stmt.executeUpdate(s);
            }

            ResultSet rsAfterAdd = stmt.executeQuery("SELECT * FROM DIRECTORY");
            List<City> citiesFromDB =new ArrayList<>();
            while (rsAfterAdd.next()) {
                citiesFromDB.add(new City(rsAfterAdd.getInt("ID"),
                                        rsAfterAdd.getString("name"),
                                        rsAfterAdd.getString("region"),
                                        rsAfterAdd.getString("district"),
                                        rsAfterAdd.getInt("population"),
                                        rsAfterAdd.getInt("foundation")));
            }

            Util.menu();
            Scanner sc = new Scanner(System.in);
            int action = sc.nextInt();
            while (action != 6) {

                switch (action) {
                    case 1:
                        Util.getCities(citiesFromDB);
                        Util.menu();
                        break;
                    case 2:
                        Util.getCities(citiesFromDB.stream().sorted(Comparator.comparing(City::getName, String.CASE_INSENSITIVE_ORDER).reversed())
                                .collect(Collectors.toList()));
                        Util.menu();
                        break;
                    case 3:
                        Util.getCities(citiesFromDB.stream().sorted(Comparator.comparing(City::getDistrict)
                                .thenComparing((City::getName)).reversed()).collect(Collectors.toList()));
                        Util.menu();
                        break;
                    case 4:
                        City[] citiesArray = citiesFromDB.stream().toArray(City[]::new);
                        Util.findMaxPopulation(citiesArray);
                        Util.menu();
                        break;
                    case 5:
                        Map<String, Long> countOfCityInDistrict = citiesFromDB.stream()
                                .collect(Collectors.groupingBy(City::getDistrict, Collectors.counting()));
                        Util.countOfCityInDistrict(countOfCityInDistrict);
                        Util.menu();
                        break;
                    default:
                        System.out.println("Введите цифру от 1 до 6");
                }
                action = sc.nextInt();
            }

        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { rsBeforeAdd.close(); } catch (SQLException ignored) { }
            try { stmt.close(); } catch (SQLException ignored) { }
            try { con.close(); } catch (SQLException ignored) { }
        }
    }
}