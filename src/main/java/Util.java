import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Util {
    public static List<City> parseDirectory(List<Integer> excludedId, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        scanner.useDelimiter(";");
        List<City> cities = new ArrayList<>();
        while (scanner.hasNext()) {
            int id = scanner.nextInt();
            String name = scanner.next();
            String region = scanner.next();
            String district = scanner.next();
            int population = scanner.nextInt();
            int foundation = scanner.nextInt();
            if (excludedId.contains(id)){
                continue;
            }
            cities.add(new City(id, name, region, district, population, foundation));
        }
        return cities;
    }
    public static List<City> parseDirectory(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        scanner.useDelimiter(";");
        List<City> cities = new ArrayList<>();
        while (scanner.hasNext()) {
            int id = scanner.nextInt();
            String name = scanner.next();
            String region = scanner.next();
            String district = scanner.next();
            int population = scanner.nextInt();
            int foundation = scanner.nextInt();
            cities.add(new City(id, name, region, district, population, foundation));
        }
        return cities;
    }

    public static List<String> convertCityToStatementStr(List<City> cities) {

        String sql = "INSERT INTO Registration " +
                "VALUES (100, 'Zara', 'Ali', 18)";
        List <String> str = new ArrayList<>();
        for (City c: cities) {
            str.add("INSERT INTO DIRECTORY VALUES (" + c.getId()  + ", '"
                                                        + c.getName() + "', '"
                                                        + c.getRegion() + "', '"
                                                        + c.getDistrict() + "', "
                                                        + c.getPopulation() + ", "
                                                        + c.getFoundation() + ")");
        }
        return str;
    }

    public static void menu() {
        System.out.println("----------------------------------------------------------");
        System.out.println("Выберите требуемое действие:");
        System.out.println("1) Список городов.");
        System.out.println("2) Отсортированный список городов.");
        System.out.println("3) Отсортированный список городов по федеральному округу.");
        System.out.println("4) Поиск города с наибольшим количеством жителей.");
        System.out.println("5) Поиск количества городов в разрезе регионов.");
        System.out.println("6) Выход.");
        System.out.println("----------------------------------------------------------");
    }

    public static void getCities(List<City> cities) {
        for (City c: cities) {
            System.out.println(c);
        }
    }

    public static void findMaxPopulation(City[] cities) {
        int index = 0;
        int maxPopulation = 0;

        for (int i = 0; i < cities.length; i++) {
            if (cities[i].getPopulation() > maxPopulation) {
                index = cities[i].getId();
                maxPopulation = cities[i].getPopulation();
            }
        }
        System.out.println("[" + index + "] = " + maxPopulation);
    }

    public static void countOfCityInDistrict(Map<String, Long> districts) {
        for (Map.Entry<String, Long> entry: districts.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
