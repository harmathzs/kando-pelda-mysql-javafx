package org.example.kandopeldamysqljavafx;

import java.sql.*;
import java.util.*;

public class MysqlService {
    public static boolean isRunningTest = false;

    public static Map<String, List<Map<String, Object>>> getDogsAndOwners(String hostname, String dbname, String username, String pass) {
        Map<String, List<Map<String, Object>>> results = new HashMap<>();
        results.put("dogs", new ArrayList<>());
        results.put("dog_owners", new ArrayList<>());

        String url = "jdbc:mysql://"+hostname+":3306/"+dbname;

        String query = """
            SELECT
              dogs.id AS dog_id,
              dogs.name AS dog_name,
              dogs.age,
              dogs.male,
              dogs.ownerid,
              dog_owners.id AS owner_id,
              dog_owners.name AS owner_name
            FROM
              dogs
            LEFT JOIN
              dog_owners ON dogs.ownerid = dog_owners.id
            
            UNION
            
            SELECT
              dogs.id AS dog_id,
              dogs.name AS dog_name,
              dogs.age,
              dogs.male,
              dogs.ownerid,
              dog_owners.id AS owner_id,
              dog_owners.name AS owner_name
            FROM
              dog_owners
            LEFT JOIN
              dogs ON dogs.ownerid = dog_owners.id
            """;
        // WHERE dogs.id IS NULL;


        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection conn = DriverManager.getConnection(url, username, pass);

            // Create statement and execute query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Process the result set
            while (rs.next()) {
                Map<String, Object> dogMap = new HashMap<>();

                int dog_id = rs.getInt("dog_id");
                dogMap.put("dog_id", dog_id);
                String dog_name = rs.getString("dog_name");
                dogMap.put("dog_name", dog_name);
                float age = rs.getFloat("age");
                dogMap.put("age", age);
                boolean male = rs.getBoolean("male");
                dogMap.put("male", male);
                int ownerid = rs.getInt("ownerid");
                dogMap.put("ownerid", ownerid);

                if (dog_name!=null) results.get("dogs").add(dogMap);

                Map<String, Object> ownerMap = new HashMap<>();

                int owner_id = rs.getInt("owner_id");
                ownerMap.put("owner_id", owner_id);
                String owner_name = rs.getString("owner_name");
                ownerMap.put("owner_name", owner_name);

                if (owner_name!=null) results.get("dog_owners").add(ownerMap);

                //System.out.println("dog_id: " + dog_id + ", dog_name: " + dog_name + ", owner_id: " + owner_id + ", owner_name: " + owner_name);
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static List<Dog> queryDogs(String hostname, String dbname, String username, String pass, Set<Integer> ids) {
        List<Dog> dogs = new ArrayList<>();

        String url = "jdbc:mysql://"+hostname+":3306/" + dbname + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        // Build the SQL query
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT d.id AS dog_id, d.name AS dog_name, d.age, d.male, ")
                .append("o.id AS owner_id, o.name AS owner_name ")
                .append("FROM dogs d LEFT JOIN dog_owners o ON d.ownerid = o.id ");

        if (ids != null && !ids.isEmpty()) {
            sb.append("WHERE d.id IN (");
            sb.append(String.join(",", Collections.nCopies(ids.size(), "?")));
            sb.append(")");
        }

        try (
                Connection conn = DriverManager.getConnection(url, username, pass);
                PreparedStatement stmt = conn.prepareStatement(sb.toString())
        ) {
            if (ids != null && !ids.isEmpty()) {
                int idx = 1;
                for (int id : ids) {
                    stmt.setInt(idx++, id);
                }
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int dogId = rs.getInt("dog_id");
                String dogName = rs.getString("dog_name");
                float age = rs.getFloat("age");
                boolean male = rs.getBoolean("male");

                int ownerId = rs.getInt("owner_id");
                String ownerName = rs.getString("owner_name");
                Owner owner = null;
                if (!rs.wasNull()) {
                    owner = new Owner(ownerId, ownerName);
                }

                Dog dog = new Dog(dogId, dogName, age, male, owner);
                dogs.add(dog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dogs;
    }

    public static List<Owner> queryOwners(String hostname, String dbname, String username, String pass, Set<Integer> ids) {
        List<Owner> owners = new ArrayList<>();

        String url = "jdbc:mysql://"+hostname+":3306/"+dbname;
        String query = "SELECT id, name FROM dog_owners ";
        if (ids != null && !ids.isEmpty()) {
            query+="WHERE id IN (";
            query+=String.join(",", Collections.nCopies(ids.size(), "?"));
            query+=")";
        }
        //System.out.println("query: "+query); // SELECT id, name FROM dog_owners WHERE id IN (?,?)

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection conn = DriverManager.getConnection(url, username, pass);

            // Create statement and execute query
            PreparedStatement stmt = conn.prepareStatement(query);
            if (ids != null && !ids.isEmpty()) {
                int idx = 1;
                for (int id : ids) {
                    stmt.setInt(idx++, id);
                }
            }
            ResultSet rs = stmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                if (name!=null) owners.add(new Owner(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return owners;
    }

    public static void upsertDogs(String hostname, String dbname, String username, String pass, Dog[] dogs) {
        if (dogs != null && dogs.length > 0) {
            String url = "jdbc:mysql://"+hostname+":3306/" + dbname + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String sql = "INSERT INTO dogs (id, name, age, male, ownerid) VALUES (?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "name = VALUES(name), age = VALUES(age), male = VALUES(male), ownerid = VALUES(ownerid)";

            try (Connection conn = DriverManager.getConnection(url, username, pass);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                for (Dog dog : dogs) {
                    if (dog.getId() == null) {
                        stmt.setNull(1, java.sql.Types.INTEGER); // Let MySQL auto-generate the id
                    } else {
                        stmt.setInt(1, dog.getId());
                    }
                    stmt.setString(2, dog.getName());
                    stmt.setFloat(3, dog.getAge());
                    stmt.setBoolean(4, dog.isMale());
                    // If dog.getOwner() may be null, handle accordingly
                    if (dog.getOwner() != null) {
                        stmt.setInt(5, dog.getOwner().getId());
                    } else {
                        stmt.setNull(5, java.sql.Types.INTEGER);
                    }
                    stmt.addBatch();
                }

                if (!isRunningTest) stmt.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void upsertOwners(String hostname, String dbname, String username, String pass, Owner[] owners) {
        if (owners!=null && owners.length>0) {
            String url = "jdbc:mysql://"+hostname+":3306/"+dbname+"?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String sql = "INSERT INTO dog_owners (id, name) VALUES (?, ?) " +
                    "ON DUPLICATE KEY UPDATE name = VALUES(name)";

            try (Connection conn = DriverManager.getConnection(url, username, pass);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                for (Owner owner : owners) {
                    if (owner.getId() == null) {
                        stmt.setNull(1, java.sql.Types.INTEGER); // Let MySQL auto-generate the id
                    } else {
                        stmt.setInt(1, owner.getId());
                    }
                    stmt.setString(2, owner.getName());
                    stmt.addBatch();
                }


                if (!isRunningTest) stmt.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void deleteDogs(String hostname, String dbname, String username, String pass, Set<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            String url = "jdbc:mysql://"+hostname+":3306/" + dbname + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String sql = "DELETE FROM dogs WHERE id IN (" + String.join(",", Collections.nCopies(ids.size(), "?")) +
                    ")";

            try (Connection conn = DriverManager.getConnection(url, username, pass);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                int idx = 1;
                for (int id : ids) {
                    stmt.setInt(idx++, id);
                }

                if (!isRunningTest) stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteOwners(String hostname, String dbname, String username, String pass, Set<Integer> ids) {
        if (ids!=null && !ids.isEmpty()) {
            String url = "jdbc:mysql://"+hostname+":3306/" + dbname + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String sql = "DELETE FROM dog_owners WHERE id IN (" + String.join(",", Collections.nCopies(ids.size(), "?")) +
                    ")";

            try (Connection conn = DriverManager.getConnection(url, username, pass);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                int idx = 1;
                for (int id : ids) {
                    stmt.setInt(idx++, id);
                }

                if (!isRunningTest) stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

