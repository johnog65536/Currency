//import static org.junit.Assert.assertArrayEquals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.table.TableColumnModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Point;

public class Wallet {
	
    static String address1 =  "19mP9FKrXqL46Si58pHdhGKow88SUPy1V8";
    static String address2 =  "16825vLBRJK3flCjoXXajJsRV8P1bPK8MJ";
    static String address3 =  "18dhDHyHuVJrMSZ9VDmdWxY4zeS1BHM6ew";
	
	public static void main(String args[]) { 
		
		   login();

	}
	
	public static void login() { 
		
	 // Create frame
		JFrame f=new JFrame("Cryptobodgers Login"); 
		
	//	Username field
	    JLabel l1=new JLabel("Username:");    
	    l1.setBounds(20,20, 80,30); 
	    final JTextField text = new JTextField();  
	    text.setBounds(100,20, 100,30);
	    f.add(l1); f.add(text);  
	   	    
	//	Password field	    
	    final JPasswordField value = new JPasswordField();   
	    value.setBounds(100,75,100,30);   
 	    JLabel l2=new JLabel("Password:");    
	    l2.setBounds(20,75, 80,30);
	    f.add(value); f.add(l2);
	    
	//  Login button  
	    JButton b = new JButton("Login");  
	    b.setBounds(100,120, 80,30);
	    f.add(b);  
	    
	 // Error message field  
	    final JLabel label = new JLabel();            
	    label.setBounds(20,150, 200,50);
	    f.add(label);
	               
	    f.setSize(300,300);    
	    f.setLayout(null);    
	    f.setVisible(true);  
	    
	    b.addActionListener(new ActionListener() {  
	    	public void actionPerformed(ActionEvent e) { 
	    	//	Validate login details
	    		String username = text.getText(); 
	    		String password = new String(value.getPassword());
	    		if(username.equals("Crypto")) {
	    			if(password.equals("bodger")) {
	    				 f.dispose();
	    				 home();
	    			}
	    		}
	    		String message = "Invalid login details";   
	            label.setText(message);
	         }  
	     });
	}
	
	public static void home() { 
		
	//  Temporary string values
		String transactions = "13";
		String received = "53.1";
		String sent = "57.5810994";
		String finalBalance = "173.4859726";
	    
	//  Create frame
		JFrame f=new JFrame("CryptoBodgers Home");  
	   
	//  Create Home button and add to frame
		JButton bHome=new JButton("Home");  
	    bHome.setBounds(50,250,95,30);
	    f.add(bHome);
	    
    //  Create History button and add to frame 
	    JButton bHistory=new JButton("History");  
	    bHistory.setBounds(150,250,95,30);
	    f.add(bHistory); 
	    
	//  Create Send button and add to frame
	    JButton bSend=new JButton("Send");  
	    bSend.setBounds(250,250,95,30);
	    f.add(bSend);
	    
	//  Create Receive button and add to frame		    
	    JButton bReceive=new JButton("Receive");  
	    bReceive.setBounds(350,250,95,30);
	    f.add(bReceive);
	    
    //  Create Logout button and add to frame
	    JButton bLogout=new JButton("Logout");  
	    bLogout.setBounds(450,250,95,30);
	    f.add(bLogout);
	    
	//  Create Total Transactions field and add to frame
	    JLabel l1=new JLabel("Total Transactions");  
	    l1.setBounds(75,25, 150,30);
	    JTextField t1=new JTextField();  
	    t1.setBounds(200,25, 200,30); 
	    f.add(l1); f.add(t1); 
	    
	//  Create Total Received field and add to frame
	    JLabel l2=new JLabel("Total Received");  
	    l2.setBounds(75,75, 150,30);
	    JTextField t2=new JTextField();  
	    t2.setBounds(200,75, 200,30); 
	    JLabel l5=new JLabel("BTC");  
	    l5.setBounds(425,75, 150,30);
	    f.add(l2); f.add(t2); f.add(l5);
	    
	//  Create Total Sent field and add to frame
	    JLabel l3=new JLabel("Total Sent");  
	    l3.setBounds(75,125, 150,30);
	    JTextField t3=new JTextField();  
	    t3.setBounds(200,125, 200,30); 
	    JLabel l6=new JLabel("BTC");  
	    l6.setBounds(425,125, 150,30);
	    f.add(l3); f.add(l6); f.add(t3);
	    
	//  Create Final Balance field and add to frame
	    JLabel l4=new JLabel("Final Balance");  
	    l4.setBounds(75,175, 150,30);
	    JTextField t4=new JTextField();  
	    t4.setBounds(200,175, 200,30); 
	    JLabel l7=new JLabel("BTC");  
	    l7.setBounds(425,175, 150,30);
	    f.add(t4); f.add(l4);  f.add(l7);
	
	//  Set temporary text field values 
	    t1.setText("   " + transactions);
	    t2.setText("   " + received);
	    t3.setText("   " + sent);
	    t4.setText("   " + finalBalance);
	    
	//  Size frame and make visible
	    f.setSize(650,400);  
	    f.setLayout(null);  
	    f.setVisible(true);
	    
	 // Action listener for Home button  
	    bHome.addActionListener(new ActionListener() {  
	    	public void actionPerformed(ActionEvent e) {
	    		f.dispose();   
	    		home();         
            }  
         }); 
	    
     // Action listener for History button  
	    bHistory.addActionListener(new ActionListener() {  
	    	public void actionPerformed(ActionEvent e) {       
	    		f.dispose();               	   
	    		history();
            }  
         }); 
	    
	 // Action listener for Send button      
	    bSend.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {       
            	f.dispose();   
            	send();
             }  
         }); 
	    
	 // Action listener for Receive button      
	    bReceive.addActionListener(new ActionListener() {  
	    	public void actionPerformed(ActionEvent e) {       
	    		f.dispose();   
            	receive();
            }  
         }); 	    
	    
	 // Action listener for Logout button      
	    bLogout.addActionListener(new ActionListener() {  
	    	public void actionPerformed(ActionEvent e) {       
	    		System.exit(0);
            }  
        }); 
	    
	}  
	
	public static void history() {  
	    
		JFrame f=new JFrame("CryptoBodgers History");
		
		String balance = "173.4859276";
		
		String data[][]={ {"2016-01-16  09:18","to: " + address1,"-0.11", "173.3849276"},    
				          {"2016-01-13  17:22","to: " + address1,"-0.1", "173.5959276"},
				          {"2016-01-13  13:39","to: " + address1,"-0.1","173.6959276"},
				          {"2016-01-12  16:35","to: " + address2,"-0.1","173.795927"},
				          {"2016-01-12  14:18","to: " + address2,"-0.1","173.8959276"}, 
				          {"2016-01-10  17:52","to: " + address1,"-0.101","173.9959276"},
				          {"2016-01-10  17:48","to: " + address1,"-0.101","174.0969276"}, 
				          {"2016-01-10  17:35","to: " + address1,"+53.1","173.1979276"}, 
				          {"2016-01-09  18:54","at: " + address3,"+50.001","121.0979276"}, 
				          {"2016-01-09  18:48","to: " + address1,"-1.4270994","171.0989276"}, 
				          {"2016-01-08  11:59","to: " + address1,"-5.441","172.526027"}};
		String column[]={"Name","Description","Amount", "Balance"}; 
		
    //  History table   
		final JTable jt=new JTable(data,column);  
        TableColumnModel columnModel = jt.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(220);
        columnModel.getColumn(2).setPreferredWidth(20);
        columnModel.getColumn(3).setPreferredWidth(30);

	//  Home button 
		JButton bHome=new JButton("Home");  
	    bHome.setBounds(80,250,95,30);  
	    f.add(bHome);
	    
    //  History button 
	    JButton bHistory=new JButton("History");  
	    bHistory.setBounds(180,250,95,30);  
	    f.add(bHistory);
	    
	//  Send button 
	    JButton bSend=new JButton("Send");  
	    bSend.setBounds(280,250,95,30);  
	    f.add(bSend);	
	    
	//  Receive button 		    
	    JButton bReceive=new JButton("Receive");  
	    bReceive.setBounds(380,250,95,30);  
	    f.add(bReceive);	
	    
    //  Logout button 
	    JButton bLogout=new JButton("Logout");  
	    bLogout.setBounds(480,250,95,30);  
	    f.add(bLogout);	

	//  Balance field 
	    JLabel l1=new JLabel("Balance: " + balance);  
	    l1.setBounds(20,200, 200,30);
	    f.add(l1);

	//  History scroll pane    
	    JScrollPane sp = new JScrollPane(jt);
	    f.add(sp);
	    
	    f.setSize(700,350);  
	    f.setVisible(true);
	    
	 // Action listener for Home button  
	    bHome.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {
               f.dispose();   
               home();         
               }  
         }); 
	    
     // Action listener for History button  
	    bHistory.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
                   f.dispose();               	   
            	   history();
               }  
         }); 
	    
	 // Action listener for Send button      
	    bSend.addActionListener(new ActionListener() {  
	    	public void actionPerformed(ActionEvent e) {       
                   f.dispose();   
            	   send();
             }  
         }); 
	    
	 // Action listener for Receive button      
	    bReceive.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
                   f.dispose();   
            	   receive();
             }  
         }); 	    
	    
	 // Action listener for Logout button      
	    bLogout.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
             	   System.exit(0);
             }  
         }); 
	    
	}  

	
	public static void send() {  
		
		JFrame f=new JFrame("CryptoBodgers Send Bitcoins");  
		   
	//  Home button 
		JButton bHome=new JButton("Home");  
	    bHome.setBounds(50,275,95,30);  
	    f.add(bHome);
	    
	//  History button 
	    JButton bHistory=new JButton("History");  
	    bHistory.setBounds(150,275,95,30);  
	    f.add(bHistory);
	    
	//  Send button 
	    JButton bSend=new JButton("Send");  
	    bSend.setBounds(250,275,95,30);  
	    f.add(bSend);	
	    
	//  Receive button 		    
	    JButton bReceive=new JButton("Receive");  
	    bReceive.setBounds(350,275,95,30);  
	    f.add(bReceive);	
	    
	//  Logout button 
	    JButton bLogout=new JButton("Logout");  
	    bLogout.setBounds(450,275,95,30);  
	    f.add(bLogout);	

	    JLabel l1,l2,l3,l4;
	    JTextField t1,t2; 
	    
	//  Send to field
	    l1=new JLabel("Send to");  
	    l1.setBounds(75,50, 100,30);
	    f.add(l1);
		String addresses[]={address1,address2,address3};        
		final JComboBox cb=new JComboBox(addresses);
		cb.setEditable(true);
		cb.setBounds(150,50, 300,30); 
	    f.add(cb); 
	    
	//  Quantity field
	    l2=new JLabel("Quantity");  
	    l2.setBounds(75,100, 150,30);
	    f.add(l2); 
	    
	    t2=new JTextField();  
	    t2.setBounds(150,100, 75,30); 
	    f.add(t2); 
	    
	    l3=new JLabel("BTC");  
	    l3.setBounds(250,100, 100,30);
	    f.add(l3);
	    
	//  Confirm button 
	    JButton bConfirm=new JButton("Confirm");  
	    bConfirm.setBounds(450,150,95,30);  
	    f.add(bConfirm);
	    
	//  Error message
	    l4=new JLabel(); 
	    l4.setBounds(75,150, 250,30);
	    f.add(l4);
	   
	    f.setSize(650,400);  
	    f.setLayout(null);  
	    f.setVisible(true); 
	    
	 // Action listener for Home button  
	    bHome.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {
               f.dispose();   
               home();         
               }  
         }); 
	    
     // Action listener for History button  
	    bHistory.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
                   f.dispose();               	   
            	   history();
               }  
         }); 
	    
	 // Action listener for Send button      
	    bSend.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
                   f.dispose();   
            	   send();
             }  
         }); 
	    
	 // Action listener for Receive button      
	    bReceive.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
                   f.dispose();   
            	   receive();
             }  
         }); 	    
	    
	 // Action listener for Logout button      
	    bLogout.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
           	   System.exit(0);
             }  
         }); 
	    
	 // Action listener for Confirm button      
	    bConfirm.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) { 
            	   String data = "" + cb.getSelectedItem();  
          	       int response=validateAddress(data);
          	       if(response ==0) {
          	    	   f.dispose();   
          	    	   home();
          	       }  else {
          	    	 l4.setText("invalid address format");
          	       }
             } 
         }); 
	    
	}
	
	public static void receive() {  	
		
		JFrame f=new JFrame("CryptoBodgers Receive Bitcoins");  
		   
	//  Home button 
		JButton bHome=new JButton("Home");  
	    bHome.setBounds(50,275,95,30);  
	    f.add(bHome);
	    
	//  History button 
	    JButton bHistory=new JButton("History");  
	    bHistory.setBounds(150,275,95,30);  
	    f.add(bHistory);
	    
	//  Send button 
	    JButton bSend=new JButton("Send");  
	    bSend.setBounds(250,275,95,30);  
	    f.add(bSend);	
	    
	//  Receive button 		    
	    JButton bReceive=new JButton("Receive");  
	    bReceive.setBounds(350,275,95,30);  
	    f.add(bReceive);	
	    
	//  Logout button 
	    JButton bLogout=new JButton("Logout");  
	    bLogout.setBounds(450,275,95,30);  
	    f.add(bLogout);	

	    JLabel l1,l2,l3,l4;
	    JTextField t2; 
	    
	//  Receive from to field
	    l1=new JLabel("Receive from");  
	    l1.setBounds(75,50, 100,30);
	    f.add(l1);
		String addresses[]={address1,address2,address3};        
		final JComboBox<Object> cb=new JComboBox<Object>(addresses);
		cb.setEditable(true);
		cb.setBounds(175,50, 300,30); 
	    f.add(cb); 
	    
	//  Quantity field
	    l2=new JLabel("Quantity");  
	    l2.setBounds(75,100, 150,30);
	    f.add(l2); 
	    
	    t2=new JTextField();  
	    t2.setBounds(175,100, 75,30); 
	    f.add(t2); 
	    
	    l3=new JLabel("BTC");  
	    l3.setBounds(275,100, 100,30);
	    f.add(l3);
	    
		//  Error message
	    l4=new JLabel(); 
	    l4.setBounds(75,150, 150,30);
	    f.add(l4);	    
	    
	//  Confirm button 
	    JButton bConfirm=new JButton("Confirm");  
	    bConfirm.setBounds(450,150,95,30);  
	    f.add(bConfirm);	
	   
	   
	    f.setSize(650,400);  
	    f.setLayout(null);  
	    f.setVisible(true); 
	    
		 // Action listener for Home button  
	    bHome.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {
               f.dispose();   
               home();         
               }  
         }); 
	    
     // Action listener for History button  
	    bHistory.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
                  //System.out.println("home pressed");
                   f.dispose();               	   
            	   history();
               }  
         }); 
	    
	 // Action listener for Send button      
	    bSend.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
                  //System.out.println("send pressed");
                   f.dispose();   
            	   send();
             }  
         }); 
	    
	 // Action listener for Receive button      
	    bReceive.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
                  //System.out.println("send pressed");
                   f.dispose();   
            	   receive();
             }  
               
         }); 	    
	    
	 // Action listener for Logout button      
	    bLogout.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) {       
                  //System.out.println("send pressed"); 
            	   System.exit(0);
             }  
         }); 
	    
		 // Action listener for Confirm button      
	    bConfirm.addActionListener(new ActionListener() {  
               public void actionPerformed(ActionEvent e) { 
            	   String data = "" + cb.getSelectedItem();  
          	       int response=validateAddress(data);
          	       if(response ==0) {
          	    	   f.dispose();   
          	    	   home();
          	       }  else {
          	    	 l4.setText("invalid address format");
          	       }
             } 
         }); 
	    
	}  
	
	public static int validateAddress(String address) {
		int response = 0;
	// 	Check first character = 1 or 3
		String char1 = address.substring(0, 1);
		if (!char1.equals("1")) {
			if (!char1.equals("3")) {
				response =-1;
			}
		}
	//  Check length between 26 and 35	
		int stringLength;
		stringLength=address.length();
		//if(stringLength)
			if(25 < stringLength && stringLength < 36){
				// do nothing
			} else {
				response =-1;
			}
			
			
		return response;
	}



}
