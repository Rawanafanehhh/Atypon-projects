package webapp;


public class LoginService
{
	private LoginService()
	{
	}

	public static boolean validateUser(String username , String password)
	{
		return Database.isUser(username,password);
	}


}