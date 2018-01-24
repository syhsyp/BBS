import com.bruce.control.*;
import com.bruce.service.*;
import java.util.Date;

public class test {
	public static void main(String[] args) {
//		users abc = new users();
//		System.out.println(abc.validate("syhsyp", "syh123456"));
//		abc.destroy();
//		System.out.println(abc.validate("syhsyp", "syh123456"));
//		System.out.println("Create a new user Yuhao Shui, 1996-10-01, syh19961001@gmail.com");
		
		java.sql.Date birthdate = new java.sql.Date(1996-1900, 10-1, 1);
//		users abc = new users("syhsyp", "syh123456", "Yuhao Shui", birthdate, "syh19961001@gmail.com");
//		System.out.println(abc.birthdate());
//		System.out.println(abc.email());
//		System.out.println(abc.last());
//		
//		System.out.println("Update birthdate 1997-10-01, email syhsyp@gmail.com");
//		java.sql.Date birthdate2 = new java.sql.Date(1997-1900, 10-1, 1);
//		abc.update_birthdate(birthdate2);
//		abc.update_email("syhsyp@gmail.com");
//		System.out.println(abc.birthdate());
//		System.out.println(abc.email());
//		
//		System.out.println("test user validation");
//		abc = new users();
//		System.out.println(abc.validate("syhsyp", "syh123456"));
//		
//		System.out.println("Create a new manager admin");
//		manager admin = new manager("syhsyp_admin", "syh123456", "Yuhao Shui", birthdate, "syh19961001@gmail.com");
//		admin.add_access(2);
//		System.out.println(abc.validate("syhsyp", "syh123456"));
//		System.out.println("admin level:");
//		System.out.println(admin.level());
//		System.out.println("change level to 1");
//		System.out.println("admin level:");
//		admin.add_access(1);
//		System.out.println(admin.level());
		
		users abc = new users();
		abc.validate("syhsyp", "syh123456");
//		manager admin = new manager();
//		admin.validate("syhsyp_admin", "syh123456");
//		large_plates dslr = new large_plates("单反", "DSLR");
//		System.out.println(dslr.id());
//		dslr.add_large_manager(dslr.id(), admin.id());
//		
//		manager admin_s = new manager("syhsyp1", "syhsyp", "Bruce Shui", birthdate, "shuiyuhao@yahoo.com");
//		
//		reply canon_5d4_1 = new reply("911fdf378dfb11e7ae4e00ffd655bce4", "910105688dfb11e7ae4e00ffd655bce4","Yes, definitely.");
		
		manager admin = new manager();
		admin.validate("syhsyp_admin", "syh123456");
//		System.out.println(admin.level());
//		admin.add_access(1);
//		System.out.println(admin.level());
		
//		large_plates dslr = new large_plates("单反", "DSLR");
//		small_plates canon = new small_plates("佳能", "Canon", dslr.id());
//		large_plates dslr = new large_plates("52c817c18d6711e7ae4e00ffd655bce4");
//		System.out.println(dslr.id());
//		topic canon_5d4 = new topic(canon.id(), abc.id(),"5D mark IV", "Is it a good choice to buy 5D mark IV?");
//		reply canon_5d4_1 = new reply(canon_5d4.id(), abc.id(), "Yes, definitely.");
		
//		topic canon_5d4 = new topic("634a93288e2211e7ae4e00ffd655bce4");
//		reply canon_5d4_1 =new reply("634d45bc8e2211e7ae4e00ffd655bce4");
//		small_plates canon = new small_plates("6346e96b8e2211e7ae4e00ffd655bce4");
//		large_plates dslr = new large_plates("6342c2018e2211e7ae4e00ffd655bce4");
//		dslr.destroy();
//		canon.destroy();
//		canon_5d4.destroy();
		large_plates compact = new large_plates("微单", "Mirror-less Camera");
		small_plates sony = new small_plates("索尼", "Sony", compact.id());
		small_plates fuji = new small_plates("富士", "Fuji", compact.id());
	}
}
