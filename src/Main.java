import java.awt.EventQueue;

import forms.LoginForm;

public class Main {

    public static void main(String[] args) {

    	EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LoginForm login = new LoginForm();
					login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
