import org.example.kandopeldamysqljavafx.Credentials;
import org.example.kandopeldamysqljavafx.Dog;
import org.example.kandopeldamysqljavafx.MysqlService;
import org.example.kandopeldamysqljavafx.Owner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.util.*;

public class MysqlTest {
    @BeforeClass
    public static void testBeforeAll() {
        MysqlService.isRunningTest = true;
    }
    @AfterClass
    public static void testAfterAll() {
        MysqlService.isRunningTest = false;
    }

    @Test
    public void testgetDogsAndOwners() {
        Map<String, List<Map<String, Object>>> results = MysqlService.getDogsAndOwners("localhost", "dogs_and_owners", "root", "");
        System.out.println(results);
        // {dogs=[
        // {dog_name=Fido, ownerid=1, age=3.0, male=true, dog_id=1},
        // {dog_name=Fifi, ownerid=2, age=1.0, male=false, dog_id=2},
        // {dog_name=Oscar, ownerid=1, age=4.0, male=true, dog_id=3}],
        // owners=[
        // {owner_name=John Smith, owner_id=1},
        // {owner_name=Jane Doe, owner_id=2},
        // {owner_name=John Smith, owner_id=1},
        // {owner_name=Mia Gordon, owner_id=3}]}

        // TODO - Owners can appear duplicated! See: John Smith

        Assert.assertNotNull(results);
    }

    @Test
    public void testqueryDogsWithIds() {
        Set<Integer> dogIds = new HashSet<>(Arrays.asList(1, 2));

        List<Dog> dogs = MysqlService.queryDogs("localhost", "dogs_and_owners", "root", "", dogIds);
        System.out.println(dogs); // [Dog@263121b, Dog@41dabe47]

        //Assertions.assertEquals(2, dogs.size());
    }
    @Test
    public void testqueryDogsWithoutIds() {
        List<Dog> dogs = MysqlService.queryDogs("localhost", "dogs_and_owners", "root", "", Collections.emptySet());
        System.out.println(dogs); // [Dog@691ff4fe, Dog@3d426638, Dog@1b6a9bb9]

        Assert.assertFalse(dogs.isEmpty());
    }

    @Test
    public void testqueryOwnersWithIds() {
        Set<Integer> ownerIds = new HashSet<>(Arrays.asList(1,2));
        List<Owner> owners = MysqlService.queryOwners("localhost", "dogs_and_owners", "root", "", ownerIds);
        System.out.println(owners);
        Assert.assertFalse(owners.isEmpty());
        //Assertions.assertEquals(2, owners.size());
    }

    @Test
    public void testqueryOwnersWithoutIds() {
        List<Owner> owners = MysqlService.queryOwners("localhost", "dogs_and_owners", "root", "", Collections.emptySet());
        System.out.println(owners);
        Assert.assertFalse(owners.isEmpty());
    }

    @Test
    public void testupsertDogs() {
        Dog[] dogs = new Dog[2];
        dogs[0] = new Dog(1, "Fido", 1, true, null);
        dogs[1] = new Dog(2, "Fifi", 2, false, null);
        MysqlService.upsertDogs("localhost", "dogs_and_owners", "root", "", dogs);
    }

    @Test
    public void testupsertOwners() {
        Owner[] owners = new Owner[2];
        owners[0] = new Owner(1, "John Smith");
        owners[1] = new Owner(2, "Jane Doe");
        MysqlService.upsertOwners("localhost", "dogs_and_owners", "root", "", owners);
    }

    @Test
    public void testdeleteDogs() {
        testupsertDogs();
        Set<Integer> dogIds = new HashSet<>(Arrays.asList(1,2));
        MysqlService.deleteDogs("localhost", "dogs_and_owners", "root", "", dogIds);
    }

    @Test
    public void testdeleteOwners() {
        testupsertOwners();
        Set<Integer> ownerIds = new HashSet<>(Arrays.asList(1,2));
        MysqlService.deleteOwners("localhost", "dogs_and_owners", "root", "", ownerIds);
    }

    @Test
    public void testFreeDB() {
        List<Owner> owners = MysqlService.queryOwners(Credentials.freedbHostname, Credentials.freedbDBname, Credentials.freedbUsername, Credentials.freedbPassword, null);
        System.out.println(owners);
        Assert.assertFalse(owners.isEmpty());
    }
}
