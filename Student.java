import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.applet.*;

interface Constants
{
	int STUDENTINFO = 1, SYLLABUS = 2, LECTURERCHART = 3, SSNL = 4, LECTURERINFO = 5;
	int SYLLABUSPRESENT = 6, MAXATTENDANCE = 7;
	int NOSEMCOURSE[] = {10,10,10,9,10,7,10,8};
}


//this class facilitates the viewing of existing records
class ViewInfo extends JFrame implements ActionListener
{
	JPanel p1,p2,p3;
	JLabel lbatch,lsem;
	JTextField tbatch,tsem;
	static int batch=0,sem=0;
	JButton viewSylab,lectChart,studSess,back,studInfo;
	String sbatch="";
	String ssem="";
	String year="";
	ImageIcon img;
	JLabel image;
	Container con;
	java.util.Calendar cl=new java.util.GregorianCalendar();
	//constructor
	ViewInfo()
	{
		con=getContentPane();
		img=new ImageIcon("logo.jpg");
		image=new JLabel(img);
		image.setBounds(80,120,400,400);
		con.add(image);
		p1=new JPanel();
		p1.setLayout(new GridLayout(2,2));
		lbatch=new JLabel("Enter batch year");
		p1.add(lbatch);
		tbatch=new JTextField();
		p1.add(tbatch);
		year=""+cl.get(Calendar.YEAR);
		tbatch.setText(year);
		lsem=new JLabel("Enter Semester");
		p1.add(lsem);
		tsem=new JTextField();
		p1.add(tsem);
		tsem.setText("1");
		add(p1,BorderLayout.NORTH);
		p2=new JPanel();
		viewSylab=new JButton("Syllabus Scheme");
		lectChart=new JButton("Lecturer-Chart");
		back=new JButton("Back");
		studSess=new JButton("Academic Record");
		studInfo=new JButton("Student Personal Record");
		p2.setLayout(new GridLayout(2,3));
		p2.add(studInfo);
		p2.add(viewSylab);
		p2.add(lectChart);
		p2.add(studSess);
		p2.add(back);
		add(p2,BorderLayout.SOUTH);
		studInfo.addActionListener(this);
		viewSylab.addActionListener(this);
		back.addActionListener(this);
		lectChart.addActionListener(this);
		studSess.addActionListener(this);
	}//end of constructor
	public void actionPerformed(ActionEvent ae)
	{
		JButton b=(JButton)ae.getSource();
		sbatch=tbatch.getText().toUpperCase();
		ssem=tsem.getText().toUpperCase();
		java.util.Calendar cl=new java.util.GregorianCalendar();
		if(b==back)
		{
			this.setVisible(false);
		}//if back
		if(b==viewSylab)
		{
			if(sbatch.compareTo("")==0 || ssem.compareTo("")==0)					//check for the proper entry of batch and semester
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH AND SEMESTER");
			}//if
			else if(Integer.parseInt(sbatch)>cl.get(Calendar.YEAR))
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH AND SEMESTER");
			}//else if
			else
			{
				batch=Integer.parseInt(sbatch);
				sem=Integer.parseInt(ssem);
				ViewSylab vs=new ViewSylab(batch,sem);
				vs.setTitle("Syllabus Pattern of semester"+sem);
				vs.setSize(600,600);
				vs.setVisible(true);
			}//else
		}//if viewSylab
		if(b==studInfo)
		{
			if(sbatch.compareTo("")==0)					//check for the proper entry of batch and semester
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH");
			}//if
			else if(Integer.parseInt(sbatch)>cl.get(Calendar.YEAR))
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH");
			}//else if
			else
			{
				batch=Integer.parseInt(sbatch);
				sem=Integer.parseInt(ssem);
				ViewStudRecord vsr=new ViewStudRecord(batch,sem);
				vsr.setTitle("Student Record");
				vsr.setSize(600,600);
				vsr.setVisible(true);
			}//else
		}//if studInfo
		if(b==lectChart)
		{
			if(sbatch.compareTo("")==0 || ssem.compareTo("")==0)					//check for the proper entry of batch and semester
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH OR SEM");
			}//if
			else if(Integer.parseInt(sbatch)>cl.get(Calendar.YEAR))
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH OR SEM");
			}//else if
			else
			{
				batch=Integer.parseInt(sbatch);
				sem=Integer.parseInt(ssem);
				ViewLectChart vlc=new ViewLectChart(batch,sem);
				vlc.setTitle("Lecturer Chart of semester"+sem);
				vlc.setSize(600,600);
				vlc.setVisible(true);
			}//else
		}//if lectChart
		if(b==studSess)
		{
			if(sbatch.compareTo("")==0 || ssem.compareTo("")==0)					//check for the proper entry of batch and semester
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH OR SEM");
			}//if
			else if(Integer.parseInt(sbatch)>cl.get(Calendar.YEAR))
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH");
			}//else if
			else
			{
				batch=Integer.parseInt(sbatch);
				sem=Integer.parseInt(ssem);
				SsnlPasswordChecker spc=new SsnlPasswordChecker(batch,sem);
				spc.setTitle("Sessional Details");
				spc.setSize(600,600);
				spc.setVisible(true);
			}//else
		}
	}//actionPerformed
}//class ViewInfo

//This class shows the Syllabus
class ViewSylab extends JFrame implements ActionListener
{
	static int batch,sem;
	String table="";
	JPanel p;
	JButton back,print;
	JTable tbl;

	  //constructor
	  ViewSylab(int batch,int sem)
	  {
		this.batch=batch;
		this.sem=sem;
		tbl=new JTable(14,9);
		tbl.setValueAt("Course No.",0,0);
		tbl.setValueAt("Subject",0,1);
		tbl.setValueAt("L",0,2);
		tbl.setValueAt("T",0,3);
		tbl.setValueAt("P",0,4);
		tbl.setValueAt("Theory",0,5);
		tbl.setValueAt("Sessional",0,6);
		tbl.setValueAt("Practical",0,7);
		tbl.setValueAt("Total",0,8);
		try
		{
			connectivity();
		}
	    catch(Exception e)
	    {
			JOptionPane.showMessageDialog(this,"TABLE DOESNT EXIXT");
		}//catch
		this.add(tbl,BorderLayout.CENTER);
	    p=new JPanel();
	    back=new JButton("Back");
	    print=new JButton("Print");
	    p.add(print);
	    p.add(back);
	    add(p,BorderLayout.SOUTH);
	    back.addActionListener(this);
	    print.addActionListener(this);
	  }//end of constructor

	  public void connectivity() throws Exception
	  {
	 		table="SYLLABUS_"+batch+0+sem;
	 		System.out.println("Table name is"+table);
	 		//connecting to the database
	 		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	 		Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
	 		System.out.println("Connection done");
	 		Statement st=con.createStatement();
	 		int row=1;
	 		int col=0;
	 		ResultSet rs=st.executeQuery("Select * from "+ table +"");
	 		System.out.println("table found");
	    	while(rs.next())
	 		{
	 			tbl.setValueAt(rs.getString(1),row,col);
	 			tbl.setValueAt(rs.getString(2),row,col+1);
	 			tbl.setValueAt(rs.getString(3),row,col+2);
	 			tbl.setValueAt(rs.getString(4),row,col+3);
	 			tbl.setValueAt(rs.getString(5),row,col+4);
	 			tbl.setValueAt(rs.getString(6),row,col+5);
	 			tbl.setValueAt(rs.getString(7),row,col+6);
	 			tbl.setValueAt(rs.getString(8),row,col+7);
	 			tbl.setValueAt(rs.getString(9),row,col+8);
	 			row++;
	 		}//while
	 		rs.close();
	 		st.close();
	 		con.close();
	  }//connectivity

	  public void actionPerformed(ActionEvent ae)
	  {
		 JButton b=(JButton)ae.getSource();
		 if(b==back)
		  this.setVisible(false);
		 if(b==print)
		   PrintUtilities.printComponent(this);
	  }
}//class ViewSylab

//this class allows for the viewing of student information
class ViewStudRecord extends JFrame implements ActionListener
{
	JPanel p1,p2;
	JButton back,studInfo,studSess;
	JLabel lroll;
	double roll;
	String sroll;
	JTextField troll;
	static int batch,sem;
	String table;
	ImageIcon img;
	JLabel image;
	Container con;
	 //constructor
    ViewStudRecord(int batch,int sem)
    {
		con=getContentPane();
		img=new ImageIcon("logo.jpg");
		image=new JLabel(img);
		image.setBounds(80,120,400,400);
		con.add(image);
		 this.batch=batch;
		 this.sem=sem;
		 p1=new JPanel();
		 p1.setLayout(new GridLayout(1,2));
		 lroll=new JLabel("Enter Roll No.");
		 troll=new JTextField();
		 p1.add(lroll);
		 p1.add(troll);
		 add(p1,BorderLayout.NORTH);
		 p2=new JPanel();
		 p2.setLayout(new GridLayout(1,3));
		 studInfo=new JButton("View Student's Details");
		 studSess=new JButton("View Student's Sessionals");
		 back=new JButton("Back");
		 p2.add(studInfo);
		 p2.add(studSess);
		 p2.add(back);
		 add(p2,BorderLayout.SOUTH);
		 studInfo.addActionListener(this);
		 back.addActionListener(this);
		 studSess.addActionListener(this);
     }//end of constructor

	 public void actionPerformed(ActionEvent ae)
     {
		 JButton b=(JButton)ae.getSource();
		 sroll=troll.getText().toUpperCase();
    	 if(b==back)
		 this.setVisible(false);
    	 if(b==studInfo)
		 {
			if(sroll.compareTo("")==0)
			{
			   JOptionPane.showMessageDialog(this,"ENTER ROLLNO");
		    }//if
		    else
		    {
			   roll=Double.parseDouble(sroll);
			   System.out.println("Rollno is:"+(int)roll);
			   table="STUDENTINFO_"+batch;
			   System.out.println("Table name=:"+table);
			   StudDetail sd=new StudDetail(roll,table);
			   sd.setSize(900,150);
			   sd.setTitle("Details of "+roll);
			   sd.setVisible(true);
		    }

		  }//if studInfo
		 if(b==studSess)
		  {
		   if(sroll.compareTo("")==0)
		    {
		      JOptionPane.showMessageDialog(this,"ENTER ROLLNO");
		   	}//if
		   else
		   	{
		      roll=Double.parseDouble(sroll);
		   	  ViewStudSsnl vss=new ViewStudSsnl(batch,sem,roll);
		  	  vss.setSize(900,600);
			  vss.setTitle("Sessional Marks of "+roll+" in Semester "+sem);
		 	  vss.setVisible(true);
		    }
		 }//if studSess

	   }//actionPerformed
  }//class ViewStudInfo

//this class allows to view the student's personal details
class StudDetail extends JFrame implements ActionListener
{
	JTable tbl;
	String table;
	double roll;
	JButton back,print;
	JPanel p;
	 //constructor
    StudDetail(double roll,String table)
    {
	   //connecting to the database for record Retrieval
	   p=new JPanel();
	   back=new JButton("Back");
	   print=new JButton("Print");
	   p.add(print);
	   p.add(back);
	   add(p,BorderLayout.SOUTH);
	   back.addActionListener(this);
	   print.addActionListener(this);
	   try
       {
		   this.roll=roll;
		   this.table=table;
	   	   tbl=new JTable(2,11);
	   	   tbl.setValueAt("ROLLNO",0,0);
	   	   tbl.setValueAt("NAME",0,1);
	       tbl.setValueAt("SECTION",0,2);
	   	   tbl.setValueAt("FNAME",0,3);
	   	   tbl.setValueAt("DOB",0,4);
	   	   tbl.setValueAt("ADDR",0,5);
	   	   tbl.setValueAt("PNO",0,6);
	   	   tbl.setValueAt("EMAIL",0,7);
	   	   tbl.setValueAt("GENDER",0,8);
	   	   tbl.setValueAt("10th(%)",0,9);
	   	   tbl.setValueAt("12th(%)",0,10);
	   	   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	   	   Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
	   	   Statement st=con.createStatement();
	   	   int row=1;
	   	   int col=0;
	   	   ResultSet rs=st.executeQuery("Select * from "+table +" where ROLLNO=" +roll+"");
    	   if(rs==null)
	   	   {
	   		  JOptionPane.showMessageDialog(this,"INVALID BATCH AND ROLLNO");
	   	   }//if

	   	   else
	   	   {
	   		  System.out.println("table found");
	   	      while(rs.next())
	  		  {
	   	   		tbl.setValueAt(""+rs.getString(1)+"",row,col);
	   	   		tbl.setValueAt(rs.getString(2),row,col+1);
	   	   		tbl.setValueAt(rs.getString(3),row,col+2);
	   	   		tbl.setValueAt(rs.getString(4),row,col+3);
	   	   		tbl.setValueAt(rs.getString(5),row,col+4);
	   	   		tbl.setValueAt(rs.getString(6),row,col+5);
	   	   		tbl.setValueAt(rs.getString(7),row,col+6);
	   	   		tbl.setValueAt(rs.getString(8),row,col+7);
	   	   		tbl.setValueAt(rs.getString(9),row,col+8);
	   	   		tbl.setValueAt(rs.getString(10),row,col+9);
	   	   		tbl.setValueAt(rs.getString(11),row,col+10);
	   	   	  }//while
	   		  rs.close();
	   		  st.close();
	   		  con.close();
	   		  this.add(tbl,BorderLayout.CENTER);
	   		}//else
	   	}//try
	   	catch(Exception e)
	   	{
	    	System.out.println(e);
	   		;
	   	}
      }//end of constructor

      public void actionPerformed(ActionEvent ae)
      {
		 JButton b=(JButton)ae.getSource();
		  if(b==back)
		   this.setVisible(false);
		  if(b==print)
		   PrintUtilities.printComponent(this);
	  }
}//class StudDetail

class ViewStudSsnl extends JFrame implements ActionListener
{
	static int batch,sem;
	JPanel p;
	JButton print,back;
	JTable tbl;
	double roll;
	//constructor
	ViewStudSsnl(int batch,int sem,double roll)
	{
		this.batch=batch;
		this.sem=sem;
		this.roll=roll;
		String name="";
		String courseno[] = new String[12];
		String subject[] = new String[12];
		int i=0,j=1,count=0,row=3;
		p=new JPanel();
		back=new JButton("Back");
		print=new JButton("Print");
		p.add(print);
		p.add(back);
		add(p,BorderLayout.SOUTH);
		tbl=new JTable(15,14);
		add(tbl,BorderLayout.CENTER);
		print.addActionListener(this);
		back.addActionListener(this);
		//connecting to the database
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT NAME FROM STUDENTINFO_"+batch+" WHERE ROLLNO="+roll+"");
			rs.next();
			name=rs.getString(1);
			tbl.setValueAt(name,0,0);
			tbl.setValueAt("COURSENO",2,0);
			tbl.setValueAt("SUBJECT",2,1);
			tbl.setValueAt("S1ATT",2,2);
			tbl.setValueAt("S2ATT",2,3);
			tbl.setValueAt("S3ATT",2,4);
			tbl.setValueAt("S1",2,5);
			tbl.setValueAt("S2",2,6);
			tbl.setValueAt("S3",2,7);
			tbl.setValueAt("A1",2,8);
			tbl.setValueAt("A2",2,9);
			tbl.setValueAt("A3",2,10);
			tbl.setValueAt("A4",2,11);
			tbl.setValueAt("A5",2,12);
			tbl.setValueAt("TTL SSNL",2,13);
			rs=st.executeQuery("SELECT COURSENO,SUBJECT FROM SYLLABUS_"+batch+0+sem);
			while(rs.next())
			{
				courseno[i]=rs.getString(1);
				subject[i]=rs.getString(2);
				count++;
				i++;
			}
			for(i=0;i<count;i++)
			{
				try
				{
					tbl.setValueAt(courseno[i],row,0);
					tbl.setValueAt(subject[i],row,1);
					rs=st.executeQuery("Select S1ATT, S2ATT, S3ATT,S1,S2,S3"+
					",A1,A2,A3,A4,A5,TTLSSNL from SSNL_"+courseno[i]+"_"+batch+0+sem+" WHERE ROLLNO="+roll+"");
					rs.next();
					tbl.setValueAt(rs.getInt(1),row,2);
					tbl.setValueAt(rs.getInt(2),row,3);
					tbl.setValueAt(rs.getInt(3),row,4);
					tbl.setValueAt(rs.getInt(4),row,5);
					tbl.setValueAt(rs.getInt(5),row,6);
					tbl.setValueAt(rs.getInt(6),row,7);
					tbl.setValueAt(rs.getInt(7),row,8);
					tbl.setValueAt(rs.getInt(8),row,9);
					tbl.setValueAt(rs.getInt(9),row,10);
					tbl.setValueAt(rs.getInt(10),row,11);
					tbl.setValueAt(rs.getInt(11),row,12);
					tbl.setValueAt(rs.getInt(12),row,13);
				}
				catch(Exception e){}
				row++;
			}
		}
		catch(Exception e)
		{   }
	}//end of construtor
	public void actionPerformed(ActionEvent ae)
	{
		JButton b=(JButton)ae.getSource();
		if(b==print)
		PrintUtilities.printComponent(this);
		if(b==back)
		this.setVisible(false);
	}//actionPerformed
}//class ViewStudSsnl


//This class shows the lecturer chart of the respective batch and semester
class ViewLectChart extends JFrame implements ActionListener
 {
   JPanel p;
   JTable tbl;
   static int batch,sem;
   String table,table2;
   JButton ok,print;
   int cols,rows;
   int row=1;
   static String sec="";

     //constructor
     ViewLectChart(int batch,int sem)
      {
	   try
	   {
		this.batch=batch;
		this.sem=sem;
		table="LECTCHART_"+batch+0+sem;
		table2="SYLLABUS_"+batch+0+sem;

		//connecting to the database
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
		Statement st=con.createStatement();

 	    ResultSet rs=st.executeQuery("Select "+table+".COURSENO,SUBJECT, A1,A2,A3,B1,B2,B3"+" from "+table+","+table2+" where "+table+".COURSENO="+table2+".COURSENO");
		ResultSetMetaData rsmd=rs.getMetaData();
		cols=rsmd.getColumnCount();
		tbl=new JTable(13,cols);
		for(int i=1;i<=cols;i++)				//retreiving data from the lecturer chart table
		 {
		  String name=rsmd.getColumnName(i);
		  tbl.setValueAt(name,0,i-1);
	     }
		 while(rs.next())
		 {
		   for(int i=1;i<=cols;i++)
		   {
			String str=rs.getString(i);
		    tbl.setValueAt(str,row,i-1);
		   }
		   row++;
	     }
	     sec="";
	     rs.close();
	     st.close();
	     con.close();
	     this.add(tbl,BorderLayout.CENTER);
		 p=new JPanel();
		 p.setLayout(new GridLayout(1,2));
		 ok=new JButton("Back");
		 print=new JButton("Print");
		 ok.addActionListener(this);
		 print.addActionListener(this);
		 p.add(ok);
		 p.add(print);
		 add(p,BorderLayout.SOUTH);
	  }//try
	  catch(Exception e)
	   {
		  JOptionPane.showMessageDialog(this,"DATA DOESNT EXIST");
	   }
   }//end of constructor

  public void actionPerformed(ActionEvent ae)
   {
	  JButton b=(JButton)ae.getSource();
	  if(b==ok)
	   {
	    this.setVisible(false);
	   }
	  if(b==print)
	   {
		 PrintUtilities.printComponent(this);
	   }
   }
}//class ViewLectChart

class SsnlPasswordChecker extends JFrame implements ActionListener
 {

   JPanel p1,p2;
   static int batch,sem;
   JLabel lcno,lsec;
   JButton ok,back;
   static JComboBox csec,csub;
    String scno,ssec;
   static String section="",course="",subject="";
	ImageIcon img;
	JLabel image;
	Container con;

      //constructors
      SsnlPasswordChecker(){}
      SsnlPasswordChecker(int batch,int sem)
       {
		 con=getContentPane();
		img=new ImageIcon("logo.jpg");
		image=new JLabel(img);
		image.setBounds(80,120,400,400);
		con.add(image);
		 this.batch=batch;
		 this.sem=sem;
		 p1=new JPanel();
		 lcno=new JLabel("Subject");
		 csub=new JComboBox();
		 lsec=new JLabel("Section");
		 csec=new JComboBox();
		 //retrieving initials,filled sections from database and names of the subjects
		 try
		  {
		    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
			Statement st=con.createStatement();
		    ResultSet rs=st.executeQuery("Select distinct SECTION from STUDENTINFO_"+batch+"");
		    while(rs.next())
		     {
			   String sec=rs.getString(1);
			   csec.addItem(sec);
			   System.out.println(sec);
		     }
            rs=st.executeQuery("Select SUBJECT from SYLLABUS_"+batch+0+sem+"");
            while(rs.next())
            {
			   String sub=rs.getString(1);
			   csub.addItem(sub);
			   System.out.println(sub);
		    }

			rs.close();
			st.close();
			con.close();
		  }//try
		 catch(Exception e)
		  {
			 System.out.println("in password checker: "+e);
			 e.printStackTrace();
		  }
		 p1.setLayout(new GridLayout(3,1));

		 p1.add(lcno);
		 p1.add(csub);
		 p1.add(lsec);
		 p1.add(csec);
		 add(p1,BorderLayout.NORTH);
		 p2=new JPanel();
		 ok=new JButton("Ok");
		 back=new JButton("Back");
		 p2.add(ok);
		 p2.add(back);
		 add(p2,BorderLayout.SOUTH);
		 back.addActionListener(this);
		 ok.addActionListener(this);
	  }//end of constructor

	  String getSection()
	  {
		return(section);
	  }
	  String getCourse()
	  {
	   try
	   {
		subject=(String) csub.getSelectedItem();
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
		Statement st=con.createStatement();
		String q="Select COURSENO from SYLLABUS_"+batch+0+sem+" where SUBJECT='"+subject+"'";
		System.out.println(q);
		ResultSet rs=st.executeQuery(q);
		rs.next();
		course=rs.getString(1);
		System.out.println(course);
		rs.close();
		st.close();
		con.close();
	   }
	   catch(Exception e)
	   {
		 System.out.println("in password checker :getCourse() :"+e);
		 e.printStackTrace();
	   }

		return(course);
	  }

	   public void actionPerformed(ActionEvent ae)
	    {
		  JButton b=(JButton)ae.getSource();

		  course=scno=getCourse();
		  course=course.toUpperCase();
		  section=ssec=(String) csec.getSelectedItem();
		  section=section.toUpperCase();
		  if(b==back)
		  {
			  this.dispose();
		  }
		  if(b==ok)
		   {

			if((scno.compareTo("")==0) || (ssec.compareTo("")==0))
			 {
			   JOptionPane.showMessageDialog(this,"DETAILS INCOMPLETE");
		     }
		     else
			 {
			 	ViewSsnlMarks vsm=new ViewSsnlMarks(batch,sem);
			 	vsm.setTitle("Sessional Marks");
			 	vsm.setSize(600,600);
			 	vsm.setVisible(true);
			 	this.dispose();
			}
    	  }//if ok
	    }//actionPerformed
   }//class ViewSsnlDetail

//this class facilitates the viewing of student's sessional marks
    class ViewSsnlMarks extends JFrame implements ActionListener
    {
   	 JPanel p1,p2;
   	 JTextArea jta;
   	 JButton print,back;
   	 static int batch,sem;
   	 static String course,sec;
   	 SsnlPasswordChecker spc=new SsnlPasswordChecker();

   	  //constructor
   	  ViewSsnlMarks(int batch,int sem)
   	   {
   		this.batch=batch;
   		this.sem=sem;
   		course=spc.getCourse();
   		sec=spc.getSection();
   		p1=new JPanel();
   		jta=new JTextArea();
   		jta.setSize(1100,800);
   		p1.add(jta);
   		add(p1,BorderLayout.NORTH);
   		p2=new JPanel();
   		print=new JButton("Print");
   		back=new JButton("Back");
   		p2.add(print);
   		p2.add(back);
   		add(p2,BorderLayout.SOUTH);
   		back.addActionListener(this);
   		print.addActionListener(this);
   		//retrieving sessional marks from the database
   		 try
   		  {
   			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
   			Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
   			Statement st=con.createStatement();
   			ResultSet rs;
   			jta.setText("RollNo"+"   "+"S1Att"+"   "+"S2Att"+"   "+"S3Att"+"   "+"S1"+"   "+"S2"+"   "+"S3"+"   "+"A1"+"   "+"A2"+"   "+"A3"+"   "+"A4"+"   "+"A5"+"   "+"TtlSsnl"+"\n");
   			rs=st.executeQuery("Select ROLLNO,S1ATT,S2ATT,S3ATT,S1,S2,S3"+
   					  ",A1,A2,A3,A4,A5,TTLSSNL from SSNL_"+course+"_"+batch+0+sem+" WHERE ROLLNO IN (SELECT ROLLNO FROM STUDENTINFO_"+batch+" WHERE SECTION='"+sec+"')");
   			while(rs.next())
   			 {
   			   jta.append(rs.getString(1)+"   "+rs.getString(2)+"   "+rs.getString(3)+"   "+rs.getString(4)+"   "+rs.getString(5)+"   "+rs.getString(6)+"   "+rs.getString(7)+"   "+rs.getString(8)+"   "+rs.getString(9)+"   "+rs.getString(10)+"   "+rs.getString(11)+"   "+rs.getString(12)+"   "+rs.getString(13)+"\n");
   			 }
   			jta.setEditable(false);
   			st.close();
   			con.close();
   		  }//try
   		  catch(Exception e)
   		   {
   			 System.out.println("in viewssnlmarks: "+e);
   		   }
   	     }//end of constructor

   	    public void actionPerformed(ActionEvent ae)
   	     {
   		   JButton b=(JButton)ae.getSource();
   		    if(b==back)
   		    {
				this.dispose();
		 	}
   		    if(b==print)
   		      PrintUtilities.printComponent(this);
   		 }//actionPerformed
     }//class ViewSsnlMarks

class SearchPasswordChecker extends JFrame implements ActionListener
{
	JOptionPane jop = new JOptionPane();
	JPanel p1,p2;
	static int batch,sem, percent, SSNLCRITERIA;
	JLabel lcriteria;
	JButton ok,back;
	static JComboBox ccriteria;
	String scriteria;
	ImageIcon img;
	JLabel image;
	Container con;
	//constructors
	SearchPasswordChecker(){}
	SearchPasswordChecker(int batch,int sem)
	{
		con=getContentPane();
		img=new ImageIcon("logo.jpg");
		image=new JLabel(img);
		image.setBounds(80,120,400,400);
		con.add(image);
		this.batch=batch;
		this.sem=sem;
		p1=new JPanel();
		lcriteria=new JLabel("Criteria");
		ccriteria=new JComboBox();
		ccriteria.addItem("----------------Criteria----------------");
		ccriteria.addItem("Attendance upto S1 & Sessional 1");
		ccriteria.addItem("Attendance upto S2 & Sessional 2");
		ccriteria.addItem("Attendance upto S3 & Sessional 3");
		p1.setLayout(new GridLayout(1,2));
		p1.add(lcriteria);
		p1.add(ccriteria);
		add(p1,BorderLayout.NORTH);
		p2=new JPanel();
		ok=new JButton("Ok");
		back=new JButton("Back");
		p2.add(ok);
		p2.add(back);
		add(p2,BorderLayout.SOUTH);
		back.addActionListener(this);
		ok.addActionListener(this);
	}//end of constructor
	int getPercent()
	{
		return(percent);
	}
	public void actionPerformed(ActionEvent ae)
	{
		JButton b=(JButton)ae.getSource();
		scriteria=(String)ccriteria.getSelectedItem();
		scriteria=scriteria.toUpperCase();
		if(b==back)
		{
			this.dispose();
		}
		if(b==ok)
		{
			String stri="";
			if(scriteria.compareTo("ATTENDANCE UPTO S1 & SESSIONAL 1")==0)
			{
				jop.setWantsInput(true);
				stri=jop.showInputDialog("Enter Shortlisting %age");
				if(stri==null){}
				else
				{
					percent=Integer.parseInt(stri);
					SSNLCRITERIA=1;
					ViewList vl=new ViewList(batch,sem,SSNLCRITERIA);
					vl.setTitle("Shortlisted Students");
					vl.setSize(1400,700);
					vl.setVisible(true);
				}
			}
			else if(scriteria.compareTo("ATTENDANCE UPTO S2 & SESSIONAL 2")==0)
			{
				jop.setWantsInput(true);
				stri=jop.showInputDialog("Enter Shortlisting %age");
				if(stri==null){}
				else
				{
					percent=Integer.parseInt(stri);
					SSNLCRITERIA=2;
					ViewList vl=new ViewList(batch,sem,SSNLCRITERIA);
					vl.setTitle("Shortlisted Students");
					vl.setSize(1400,700);
					vl.setVisible(true);
				}
			}
			else if(scriteria.compareTo("ATTENDANCE UPTO S3 & SESSIONAL 3")==0)
			{
				jop.setWantsInput(true);
				stri=jop.showInputDialog("Enter Shortlisting %age");
				if(stri==null){}
				else
				{
					percent=Integer.parseInt(stri);
					SSNLCRITERIA=3;
					ViewList vl=new ViewList(batch,sem,SSNLCRITERIA);
					vl.setTitle("Shortlisted Students");
					vl.setSize(1400,700);
					vl.setVisible(true);
				}
			}
		}//if ok
	}//actionPerformed
}//

class ViewList extends JFrame implements ActionListener,Constants
{
	JPanel p1,p2;
	JTextArea jta;
	JButton print,back;
	String rollno;
	float limit;
	int i=0,satt,ssnl;
	static int batch,sem,criteria,percent;
	SearchPasswordChecker sspc= new SearchPasswordChecker();
	static int flag=1;
	String courseno[]= new String[NOSEMCOURSE[sem]];
	String course[]= new String[NOSEMCOURSE[sem]];

	//constructor
	ViewList(int batch,int sem,int criteria)
	{
		this.batch=batch;
		this.sem=sem;
		this.criteria=criteria;System.out.println(criteria);
   		int maxatt[]= new int[NOSEMCOURSE[sem]];
   		float limit[]= new float[NOSEMCOURSE[sem]];
   		percent=sspc.getPercent();System.out.println(percent);
   		p1=new JPanel();
   		jta=new JTextArea();
   		jta.setSize(800,700);
   		p1.add(jta);
   		add(p1,BorderLayout.NORTH);
   		p2=new JPanel();
   		print=new JButton("Generate Report");
   		back=new JButton("Back");
   		p2.add(print);
   		p2.add(back);
   		add(p2,BorderLayout.SOUTH);
   		back.addActionListener(this);
   		print.addActionListener(this);
   		try
   		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
   			Connection con1=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
   			Statement st=con.createStatement();
   			Statement st1=con1.createStatement();
   			System.out.println("iiiiiiiii");
   			ResultSet rs=st.executeQuery("Select COURSENO, SUBJECT from SYLLABUS_"+batch+0+sem+"");
			while(rs.next())
			{
				courseno[i]=rs.getString(1);System.out.println(courseno[i]);
				course[i]=rs.getString(2);System.out.println(course[i]);
				i++;
			}
			if(criteria==1)
			{
				for(i=0;i<NOSEMCOURSE[sem];i++)
				{//i=0;
					rs=st.executeQuery("SELECT S1ATT FROM MAXATTENDANCE_"+batch+0+sem+" WHERE COURSENO='"+courseno[i]+"'");
					while(rs.next())
					{
						maxatt[i] = rs.getInt(1);
						System.out.println(maxatt[i]);
						limit[i]=((percent*maxatt[i])/100);
						System.out.println(limit[i]);
					}
				}
				rs=st.executeQuery("Select ROLLNO from STUDENTINFO_"+batch+"");
				while(rs.next())
				{
					rollno=rs.getString(1);
					double roll= Double.parseDouble(rollno);
					System.out.println(roll);
					for(i=0;i<NOSEMCOURSE[sem];i++)
					{
						ResultSet rs1=st1.executeQuery("SELECT S1ATT FROM SSNL_"+courseno[i]+"_"+batch+0+sem+" WHERE ROLLNO="+roll+"");
						rs1.next();
						satt = rs1.getInt(1);
						System.out.println(satt);
						if(satt<limit[i])
						{
							if(flag==1)
							{
								jta.append("\n"+rollno+"\t"+"Short Attendance:-");
								flag=0;
							}
							jta.append(course[i]+", ");
						}
						rs1.close();
					}
					flag=1;
					{
						jta.append("\t"+"Unsatisfactory Sessionals:-");
						for(i=0;i<NOSEMCOURSE[sem];i++)
						{
							ResultSet rs1=st1.executeQuery("SELECT S1 FROM SSNL_"+courseno[i]+"_"+batch+0+sem+" WHERE ROLLNO="+roll+"");
							rs1.next();
							ssnl = rs1.getInt(1);
							System.out.println(ssnl);
							if(ssnl<8)
							{
									jta.append(course[i]+", ");
							}
							rs1.close();
						}

					}
				}
			}

			if(criteria==2)
			{
				for(i=0;i<NOSEMCOURSE[sem];i++)
				{
					rs=st.executeQuery("SELECT S2ATT FROM MAXATTENDANCE_"+batch+0+sem+" WHERE COURSENO='"+courseno[i]+"'");
					while(rs.next())
					{
						maxatt[i] = rs.getInt(1);
						System.out.println(maxatt[i]);
						limit[i]=((percent*maxatt[i])/100);
						System.out.println(limit[i]);
					}
				}
				rs=st.executeQuery("Select ROLLNO from STUDENTINFO_"+batch+"");
				while(rs.next())
				{
					rollno=rs.getString(1);
					double roll= Double.parseDouble(rollno);
					System.out.println(roll);
					for(i=0;i<NOSEMCOURSE[sem];i++)
					{
						ResultSet rs1=st1.executeQuery("SELECT S2ATT FROM SSNL_"+courseno[i]+"_"+batch+0+sem+" WHERE ROLLNO="+roll+"");
						rs1.next();
						satt = rs1.getInt(1);
						System.out.println(satt);
						if(satt<limit[i])
						{
							if(flag==1)
							{
								jta.append("\n"+rollno+"\t"+"Short Attendance:-");
								flag=0;
							}
							jta.append(course[i]+", ");
						}
						rs1.close();
					}
					flag=1;
					{
						jta.append("\t"+"Unsatisfactory Sessionals:-");
						for(i=0;i<NOSEMCOURSE[sem];i++)
						{
							ResultSet rs1=st1.executeQuery("SELECT S2 FROM SSNL_"+courseno[i]+"_"+batch+0+sem+" WHERE ROLLNO="+roll+"");
							rs1.next();
							ssnl = rs1.getInt(1);
							System.out.println(ssnl);
							if(ssnl<8)
							{
								jta.append(course[i]+", ");
							}
							rs1.close();
						}
					}
				}
			}
			if(criteria==3)
			{
				for(i=0;i<NOSEMCOURSE[sem];i++)
				{
					rs=st.executeQuery("SELECT S3ATT FROM MAXATTENDANCE_"+batch+0+sem+" WHERE COURSENO='"+courseno[i]+"'");
					while(rs.next())
					{
						maxatt[i] = rs.getInt(1);
						System.out.println(maxatt[i]);
						limit[i]=((percent*maxatt[i])/100);
						System.out.println(limit[i]);
					}
				}
				rs=st.executeQuery("Select ROLLNO from STUDENTINFO_"+batch+"");
				while(rs.next())
				{
					rollno=rs.getString(1);
					double roll= Double.parseDouble(rollno);
					System.out.println(roll);
					for(i=0;i<NOSEMCOURSE[sem];i++)
					{
						ResultSet rs1=st1.executeQuery("SELECT S3ATT FROM SSNL_"+courseno[i]+"_"+batch+0+sem+" WHERE ROLLNO="+roll+"");
						rs1.next();
						satt = rs1.getInt(1);
						System.out.println(satt);
						if(satt<limit[i])
						{
							if(flag==1)
							{
								jta.append("\n"+rollno+"\t"+"Short Attendance:-");
								flag=0;
							}
							jta.append(course[i]+", ");
						}
						rs1.close();
					}
					flag=1;
					{
						jta.append("\t"+"Unsatisfactory Sessionals:-");
						for(i=0;i<NOSEMCOURSE[sem];i++)
						{
							ResultSet rs1=st1.executeQuery("SELECT S3 FROM SSNL_"+courseno[i]+"_"+batch+0+sem+" WHERE ROLLNO="+roll+"");
							rs1.next();
							ssnl = rs1.getInt(1);
							System.out.println(ssnl);
							if(ssnl<8)
							{
								jta.append(course[i]+", ");
							}
							rs1.close();
						}
					}
				}
			}
   			st.close();
   			con.close();
   		}//try
   		catch(Exception e)
   		{
			System.out.println("in viewssnlmarks: "+e);
		}
	}//end of constructor
	public void actionPerformed(ActionEvent ae)
	{
		JButton b=(JButton)ae.getSource();
		if(b==back)
		{
			this.dispose();
		}
		if(b==print)
		{
			int ssnlmarks[]= new int[NOSEMCOURSE[sem]];
			String str =jta.getText();
			StringTokenizer stkn = new StringTokenizer(str,"\n");
			String line="", rollno="",attsubj="",failsubj="",name="",fname="",addr="";
			try
			{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
			Statement st=con.createStatement();
   			ResultSet rs;
			RandomAccessFile raf = new RandomAccessFile("Report.txt","rws");
			java.util.Calendar cl=new java.util.GregorianCalendar();
			while(stkn.hasMoreTokens())
			{
				line=stkn.nextToken();
				StringTokenizer stkn1 = new StringTokenizer(line,"\t");
				rollno=stkn1.nextToken();
				attsubj=stkn1.nextToken();
				failsubj=stkn1.nextToken();
				rs=st.executeQuery("Select NAME,FNAME,ADDR from STUDENTINFO_"+batch+" WHERE ROLLNO='"+rollno+"'");
				rs.next();
				name=rs.getString(1);
				fname=rs.getString(2);
				addr=rs.getString(3);
				rs.close();
				if(criteria==1)
				{
					for(i=0;i<NOSEMCOURSE[sem];i++)
					{
						rs=st.executeQuery("SELECT S1 FROM SSNL_"+courseno[i]+"_"+batch+0+sem+" WHERE ROLLNO="+rollno+"");
						rs.next();
						ssnlmarks[i] = rs.getInt(1);
					}
				}
				if(criteria==2)
				{
					for(i=0;i<NOSEMCOURSE[sem];i++)
					{
						rs=st.executeQuery("SELECT S2 FROM SSNL_"+courseno[i]+"_"+batch+0+sem+" WHERE ROLLNO="+rollno+"");
						rs.next();
						ssnlmarks[i] = rs.getInt(1);
					}
				}
				if(criteria==3)
				{
					for(i=0;i<NOSEMCOURSE[sem];i++)
					{
						rs=st.executeQuery("SELECT S3 FROM SSNL_"+courseno[i]+"_"+batch+0+sem+" WHERE ROLLNO="+rollno+"");
						rs.next();
						ssnlmarks[i] = rs.getInt(1);
					}
				}
				File filename=new File("doc.txt");
				FileReader fin=new FileReader(filename);
				BufferedReader br=new BufferedReader(fin);
				String s="",s1="";
				String str1="";
				long len;
				while((s=br.readLine())!=null)
				{
					if(s.contains("fnam"))
					{
						s1=s.replace("fnam",fname);
						raf.writeBytes(s1+"\n");
						System.out.println(s1);
					}
					else if(s.contains("addr"))
					{
						s1=s.replace("addr",addr);
						raf.writeBytes(s1+"\n");
					}
					else if(s.contains("name"))
					{
						s1=s.replace("name",name+"("+rollno+")");
						raf.writeBytes(s1+"\n");
					}
					else if(s.contains("thanx"))
					{
						s1=s.replace("thanx",attsubj);
						raf.writeBytes(s1+"\n\n"+failsubj+"\n\n");
						raf.writeBytes("Sessional Marks:"+"\n\n");
						for(i=0;i<NOSEMCOURSE[sem];i++)
						{
							raf.writeBytes(course[i]+"|");
						}
						raf.writeBytes("\n\n");
						for(i=0;i<NOSEMCOURSE[sem];i++)
						{
							System.out.println(ssnlmarks[i]);
							raf.writeBytes(ssnlmarks[i]+" | ");
						}
					}
					else if(s.contains("###"))
					{
						s1=s.replace("###","\n\n\n\n\n\n\n\n");
						raf.writeBytes(s1);
					}
					else if(s.contains("Dated:"))
					{
						s1=s.replace("Dated:","Dated:"+cl.get(Calendar.DATE)+"/"+((cl.get(Calendar.MONTH))+1)+"/"+cl.get(Calendar.YEAR));
						raf.writeBytes(s1);
					}
					else
					{
						raf.writeBytes(s+"\n");
					}
				}
			}
			raf.close();
			st.close();
			con.close();
		}//try
		catch(Exception e)
		{
			System.out.println("in view list: "+e);
		}
		this.dispose();
	}
}//actionPerformed
}//class ViewList

class EnterStudInfo extends JFrame implements ActionListener,Constants
{
	JPanel p1,p2;
	JButton enterNew,enterSess,lectChart,sylab,maxatt,lectInfo,back,search;
	JLabel lbatch,lsem,lintl,lpasswd;
	JTextField tbatch,tsem;
	TextField tpasswd;
	Choice cintl;
	static int batch=0,sem=0,flag=0;
	static String sbatch="";
	static String ssem="";int RecordType=0;
	String str,nname="",passwd="";
	ImageIcon img;
	JLabel image;
	Container con;
	java.util.Calendar cl=new java.util.GregorianCalendar();
	//constructor
	EnterStudInfo()
	{
		con=getContentPane();
		img=new ImageIcon("logo.jpg");
		image=new JLabel(img);
		image.setBounds(80,120,400,400);
		con.add(image);
		p1=new JPanel();
		p1.setLayout(new GridLayout(5,2));
		lbatch=new JLabel("Enter batch year");
		tbatch=new JTextField();
		lsem=new JLabel("Enter Semester");
		tsem=new JTextField();
		lintl= new  JLabel("Initials");
		cintl=new Choice();
		cintl.add("LEC");
		cintl.add("CC1");
		cintl.add("CC2");
		cintl.add("CC3");
		cintl.add("CC4");
		cintl.add("HOD");
		lpasswd=new JLabel("Password");
		tpasswd=new TextField();
		tpasswd.setEchoChar('*');
		p1.add(lbatch);
		p1.add(tbatch);
		tbatch.setText(""+cl.get(Calendar.YEAR));
		p1.add(lsem);
		p1.add(tsem);
		tsem.setText("1");
		p1.add(lintl);
		p1.add(cintl);
		p1.add(lpasswd);
		p1.add(tpasswd);
		add(p1,BorderLayout.NORTH);
		back=new JButton("Back");
		back.addActionListener(this);
		p2=new JPanel();
		p2.setLayout(new GridLayout(3,3));
		enterNew=new JButton("Student Personal Record");
		enterSess=new JButton("Academic Record");
		lectChart=new JButton("Lecturer-Chart");
		sylab=new JButton("Syllabus Scheme");
		maxatt= new JButton("Course Max. Attendance");
		lectInfo=new JButton("Lecturer Info");
		search=new JButton("Shortlisting");
		p2.add(enterNew);
		p2.add(sylab);
		p2.add(lectChart);
		p2.add(enterSess);
		p2.add(maxatt);
		p2.add(lectInfo);
		p2.add(search);
		p2.add(back);
		enterNew.addActionListener(this);
		enterSess.addActionListener(this);
		lectChart.addActionListener(this);
		sylab.addActionListener(this);
		maxatt.addActionListener(this);
		lectInfo.addActionListener(this);
		search.addActionListener(this);
		add(p2,BorderLayout.SOUTH);
	}//end of constructor
	public void actionPerformed(ActionEvent ae)
	{
		sbatch=tbatch.getText().toUpperCase();
		batch=Integer.parseInt(sbatch);
		ssem=tsem.getText().toUpperCase();
		sem=Integer.parseInt(ssem);
		nname=(String)cintl.getSelectedItem();
		passwd=tpasswd.getText().toUpperCase();
		java.util.Calendar cl=new java.util.GregorianCalendar();
		JButton b=(JButton)ae.getSource();
		if(b==back)
		this.setVisible(false);
		if(b==enterNew)
		{
			if(sbatch.compareTo("")==0)					//check for the proper entry of batch and semester
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH ");
			}
			else if(Integer.parseInt(sbatch)<(cl.get(Calendar.YEAR)-1))
			{
				JOptionPane.showMessageDialog(this,"RECORD ALREADY EXIST");
			}
			else if(Integer.parseInt(sbatch)>cl.get(Calendar.YEAR))
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH");
			}
			else
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery("SELECT PASSWORD FROM LECTURERINFO WHERE INITIALS='"+nname+"'");
					rs.next();
					str=rs.getString(1);
				}
				catch(Exception e)
				{
					System.out.println("in password checker enterssnlmarks: "+e);
					e.printStackTrace();
				}
					if(passwd.compareTo(str)==0)
					{
						if(sem==1 && ((nname.compareTo("CC1")==0) || (nname.compareTo("HOD")==0)))
						{
							StudentInfo si=new StudentInfo();
							si.setTitle("Student Details");
							si.setSize(600,700);
							RecordType = STUDENTINFO;
							TableCreater tc = new TableCreater();
							tc.TableChecker(RecordType);
							si.setVisible(true);
							//this.setVisible(false);
						}
						else
						JOptionPane.showMessageDialog(this,"AUTHORIZATION FAILED ");
					}
					else
					JOptionPane.showMessageDialog(this,"INVALID INITIAL OR PASSWORD");
			}//else
		}//if enterNew
		if(b==lectChart)
		{
			if(sbatch.compareTo("")==0 || ssem.compareTo("")==0)					//check for the proper entry of batch and semester
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH OR SEM");
			}
			else if(Integer.parseInt(sbatch)>cl.get(Calendar.YEAR))
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH ");
			}
			else
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery("SELECT PASSWORD FROM LECTURERINFO WHERE INITIALS='"+nname+"'");
					rs.next();
					str=rs.getString(1);
				}
				catch(Exception e)
				{
					System.out.println("in password checker enterssnlmarks: "+e);
					e.printStackTrace();
				}
				if(passwd.compareTo(str)==0)
				{
					if(((sem==1 || sem==2) && ((nname.compareTo("CC1")==0) || (nname.compareTo("HOD")==0))) || ((sem==3 || sem==4) && ((nname.compareTo("CC2")==0) || (nname.compareTo("HOD")==0))) || ((sem==5 || sem==6) && ((nname.compareTo("CC3")==0) || (nname.compareTo("HOD")==0))) || ((sem==7 || sem==8) && ((nname.compareTo("CC4")==0) || (nname.compareTo("HOD")==0))))
					{
						RecordType = SYLLABUSPRESENT;
						TableCreater tc = new TableCreater();
						tc.TableChecker(RecordType);
					}
					else
					JOptionPane.showMessageDialog(this,"AUTHORIZATION FAILED ");
				}
				else
				JOptionPane.showMessageDialog(this,"INVALID INITIAL OR PASSWORD");
			}
		}//if lectChart
		if(b==sylab)
		{
			if(sbatch.compareTo("")==0 || ssem.compareTo("")==0)					//check for the proper entry of batch and semester
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH OR SEM");
			}//if
			else if(Integer.parseInt(sbatch)>cl.get(Calendar.YEAR))
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH");
			}
			else
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery("SELECT PASSWORD FROM LECTURERINFO WHERE INITIALS='"+nname+"'");
					rs.next();
					str=rs.getString(1);
				}
				catch(Exception e)
				{
					System.out.println("in password checker enterssnlmarks: "+e);
					e.printStackTrace();
				}
				if(passwd.compareTo(str)==0)
				{
					if(((sem==1 || sem==2) && ((nname.compareTo("CC1")==0) || (nname.compareTo("HOD")==0))) || ((sem==3 || sem==4) && ((nname.compareTo("CC2")==0) || (nname.compareTo("HOD")==0))) || ((sem==5 || sem==6) && ((nname.compareTo("CC3")==0) || (nname.compareTo("HOD")==0))) || ((sem==7 || sem==8) && ((nname.compareTo("CC4")==0) || (nname.compareTo("HOD")==0))))
					{
						//batch=Integer.parseInt(sbatch);
						//sem=Integer.parseInt(ssem);
						RecordType =SYLLABUS;
						TableCreater tc = new TableCreater();
						tc.TableChecker(RecordType);
						if(flag==0)
						{
							RecordType = MAXATTENDANCE;
							tc.TableChecker(RecordType);
							EnterSylab es=new EnterSylab(batch,sem);
							es.setSize(600,600);
							es.setTitle("Syllabus Scheme");
							es.setVisible(true);
							//this.setVisible(false);
						}
						else
						{
							flag=0;
							JOptionPane.showMessageDialog(this,"SYLLABUS ALREADY EXIST");
						}
					}
					else
					JOptionPane.showMessageDialog(this,"AUTHORIZATION FAILED ");
				}
				else
				JOptionPane.showMessageDialog(this,"INVALID INITIAL OR PASSWORD");
			}//else
		}//if sylab
		if(b==enterSess)
		{
			if(sbatch.compareTo("")==0 || ssem.compareTo("")==0)					//check for the proper entry of batch and semester
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH OR SEM");
			}//if
			else if(Integer.parseInt(sbatch)>cl.get(Calendar.YEAR))
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH");
			}
			else
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery("SELECT PASSWORD FROM LECTURERINFO WHERE INITIALS='"+nname+"'");
					rs.next();
					str=rs.getString(1);
				}
				catch(Exception e)
				{
					System.out.println("in password checker enterssnlmarks: "+e);
					e.printStackTrace();
				}
				if(passwd.compareTo(str)==0)
				{
					if(((sem==1 || sem==2) && ((nname.compareTo("CC1")==0) || (nname.compareTo("LEC")==0) || (nname.compareTo("HOD")==0))) || ((sem==3 || sem==4) && ((nname.compareTo("CC2")==0) || (nname.compareTo("HOD")==0))) || ((sem==5 || sem==6) && ((nname.compareTo("CC3")==0) || (nname.compareTo("HOD")==0))) || ((sem==7 || sem==8) && ((nname.compareTo("CC4")==0) || (nname.compareTo("HOD")==0))))
					{
						PasswordChecker pc=new PasswordChecker(batch,sem);
						pc.setSize(600,600);
						pc.setTitle("Academics Entry Login");
						pc.setVisible(true);
					}
					else
					JOptionPane.showMessageDialog(this,"AUTHORIZATION FAILED ");
				}
				else
				JOptionPane.showMessageDialog(this,"INVALID INITIAL OR PASSWORD");
			}//else

		}//if enterSess
		if(b==maxatt)
		{
			if(sbatch.compareTo("")==0 || ssem.compareTo("")==0)					//check for the proper entry of batch and semester
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH OR SEM");
			}//if
			else if(Integer.parseInt(sbatch)>cl.get(Calendar.YEAR))
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH");
			}
			else
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery("SELECT PASSWORD FROM LECTURERINFO WHERE INITIALS='"+nname+"'");
					rs.next();
					str=rs.getString(1);
				}
				catch(Exception e)
				{
					System.out.println("in password checker enterssnlmarks: "+e);
					e.printStackTrace();
				}
				if(passwd.compareTo(str)==0)
				{
					if(((sem==1 || sem==2) && ((nname.compareTo("CC1")==0) || (nname.compareTo("HOD")==0))) || ((sem==3 || sem==4) && ((nname.compareTo("CC2")==0) || (nname.compareTo("HOD")==0))) || ((sem==5 || sem==6) && ((nname.compareTo("CC3")==0) || (nname.compareTo("HOD")==0))) || ((sem==7 || sem==8) && ((nname.compareTo("CC4")==0) || (nname.compareTo("HOD")==0))))
					{
						//batch=Integer.parseInt(sbatch);
						//sem=Integer.parseInt(ssem);
						MaxAttendance ma=new MaxAttendance(batch,sem);
						ma.setSize(600,600);
						ma.setTitle("Course Maximum Attendance Entry");
						ma.setVisible(true);
						//this.setVisible(false);
					}
					else
					JOptionPane.showMessageDialog(this,"AUTHORIZATION FAILED ");
				}
				else
				JOptionPane.showMessageDialog(this,"INVALID INITIAL OR PASSWORD");
			}//else
		}
		if(b==lectInfo)
		{
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery("SELECT PASSWORD FROM LECTURERINFO WHERE INITIALS='"+nname+"'");
				rs.next();
				str=rs.getString(1);
			}
			catch(Exception e)
			{
				System.out.println("in password checker enterssnlmarks: "+e);
				e.printStackTrace();
			}
			if(passwd.compareTo(str)==0)
			{
				if(nname.compareTo("HOD")==0)
				{
					RecordType=LECTURERINFO;
					TableCreater tc = new TableCreater();
					tc.TableChecker(RecordType);
					EnterLectInfo eli=new EnterLectInfo();
					eli.setTitle("Lecturer Informtation");
					eli.setSize(600,600);
					eli.setVisible(true);
				}
				else
				JOptionPane.showMessageDialog(this,"AUTHORIZATION FAILED ");
			}
			else
			JOptionPane.showMessageDialog(this,"INVALID INITIAL OR PASSWORD");
		}
		if(b==search)
		{
			if(sbatch.compareTo("")==0 || ssem.compareTo("")==0)					//check for the proper entry of batch and semester
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH OR SEM");
			}//if
			else if(Integer.parseInt(sbatch)>cl.get(Calendar.YEAR))
			{
				JOptionPane.showMessageDialog(this,"INVALID BATCH");
			}//else if
			else
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery("SELECT PASSWORD FROM LECTURERINFO WHERE INITIALS='"+nname+"'");
					rs.next();
					str=rs.getString(1);
				}
				catch(Exception e)
				{
					System.out.println("in password checker enterssnlmarks: "+e);
					e.printStackTrace();
				}
				if(passwd.compareTo(str)==0)
				{
					if(((sem==1 || sem==2) && ((nname.compareTo("CC1")==0) || (nname.compareTo("HOD")==0))) || ((sem==3 || sem==4) && ((nname.compareTo("CC2")==0) || (nname.compareTo("HOD")==0))) || ((sem==5 || sem==6) && ((nname.compareTo("CC3")==0) || (nname.compareTo("HOD")==0))) || ((sem==7 || sem==8) && ((nname.compareTo("CC4")==0) || (nname.compareTo("HOD")==0))))
					{
						//batch=Integer.parseInt(sbatch);
						//sem=Integer.parseInt(ssem);
						SearchPasswordChecker sspc=new SearchPasswordChecker(batch,sem);
						sspc.setTitle("Choose Shortlisting Criteria");
						sspc.setSize(600,600);
						sspc.setVisible(true);
						//this.dispose();
					}
					else
					JOptionPane.showMessageDialog(this,"AUTHORIZATION FAILED ");
				}
				else
				JOptionPane.showMessageDialog(this,"INVALID INITIAL OR PASSWORD");
			}
		}

		batch=0;
		sem=0;
	}//actionPerformed
	void setFlag()
	{
		flag=1;
	}
	String getBatch()
	{
		return(sbatch.trim());
	}
	String getSem()
	{
		return(ssem.trim());
	}
}//classEnterStudInfo

// This class facilitates the entry of the new students' record
class StudentInfo extends JFrame implements ActionListener
{
   EnterStudInfo esi=new EnterStudInfo();
   JPanel p1,p2;
   JLabel lroll,lsection,lsname,lfname,ldob,laddr,lphone,lemail,lgender,lmetric,ltwelth;
   JTextField troll,tsname,tfname,tdob,taddr,tphone,temail;
   JButton ok,cancel;
   static JComboBox csection,cgender,cmetric,ctwelth,c1,c2,c3;
   static String batch="";
   java.util.Calendar cl=new java.util.GregorianCalendar();
    //constructor
    StudentInfo()
     {
	   Container cont=getContentPane();
	   cont.setLayout(null);
	   lroll=new JLabel("RollNo");
	   lroll.setBounds(20,50,70,40);
	   lsection=new JLabel("Section");
	   lsection.setBounds(20,100,70,40);
	   lsname=new JLabel("Student's Name");
	   lsname.setBounds(20,150,120,40);
	   lfname=new JLabel("Father's Name");
	   lfname.setBounds(20,200,150,40);
	   ldob=new JLabel("Date Of Birth");
	   ldob.setBounds(20,250,170,40);
	   laddr=new JLabel("Address");
	   laddr.setBounds(20,300,70,40);
	   lphone=new JLabel("ContactNo");
	   lphone.setBounds(20,350,70,40);
	   lemail=new JLabel("Email ID");
	   lemail.setBounds(20,400,70,40);
	   lgender=new JLabel("Gender");
	   lgender.setBounds(20,450,50,40);
	   lmetric=new JLabel("10th(%)");
	   lmetric.setBounds(20,500,70,40);
	   ltwelth=new JLabel("12th(%)");
	   ltwelth.setBounds(20,550,70,40);
	   troll=new JTextField();
	   troll.setBounds(120,60,80,20);
	   csection=new JComboBox();
	   csection.setBounds(120,110,70,20);
	   csection.addItem("A1");
	   csection.addItem("A2");
	   csection.addItem("A3");
	   csection.addItem("B1");
	   csection.addItem("B2");
	   csection.addItem("B3");
	   tsname=new JTextField();
	   tsname.setBounds(120,160,120,20);
	   tfname=new JTextField();
	   tfname.setBounds(120,210,120,20);
	   JLabel m=new JLabel("Month");
	   m.setBounds(220,260,40,20);
	   c1=new JComboBox();
	   c1.addItem("Jan");
	   c1.addItem("Feb");
	   c1.addItem("Mar");
	   c1.addItem("Apr");
	   c1.addItem("May");
	   c1.addItem("June");
	   c1.addItem("July");
	   c1.addItem("Aug");
	   c1.addItem("Sept");
	   c1.addItem("Oct");
	   c1.addItem("Nov");
	   c1.addItem("Dec");
	   c1.setBounds(270,260,70,25);
	   c2=new JComboBox();
	   String str="";
	   JLabel d=new JLabel("Date");
	   d.setBounds(120,260,30,20);
	   for(int i=1;i<=31;i++)
	   	{
	   		str=""+i;
	   		c2.addItem(str);
	   	}
	   	c2.setBounds(155,260,50,25);
	   	JLabel y=new JLabel("Year");
	   	y.setBounds(350,260,40,20);

	   	c3=new JComboBox();
	   	for(int i=1960;i<=cl.get(Calendar.YEAR);i++)
	   	{
	   		str=""+i;
	   		c3.addItem(str);
	   	}
	   	c3.setBounds(400,260,60,25);
	   taddr=new JTextField();
	   taddr.setBounds(120,310,140,20);
	   tphone=new JTextField();
	   tphone.setBounds(120,360,100,20);
	   temail=new JTextField();
	   temail.setBounds(120,410,140,20);
	   cgender=new JComboBox();
	   cgender.setBounds(120,460,80,20);
	   cgender.addItem("Male");
	   cgender.addItem("Female");
	   cmetric=new JComboBox();
	   cmetric.setBounds(120,510,40,20);
	   ctwelth=new JComboBox();
	   ctwelth.setBounds(120,560,40,20);

	   for(int i=50;i<=100;i++)
	   {
		cmetric.addItem(""+i);
		ctwelth.addItem(""+i);
	   }
	   cont.add(lroll);
	   cont.add(troll);
	   cont.add(lsection);
	   cont.add(csection);
	   cont.add(lsname);
	   cont.add(tsname);
	   cont.add(lfname);
	   cont.add(tfname);
	   cont.add(ldob);
       cont.add(d);
	   cont.add(c2);
	   cont.add(m);
	   cont.add(c1);
	   cont.add(y);
	   cont.add(c3);
	   cont.add(laddr);
	   cont.add(taddr);
	   cont.add(lphone);
	   cont.add(tphone);
	   cont.add(lemail);
	   cont.add(temail);
	   cont.add(lgender);
	   cont.add(cgender);
	   cont.add(lmetric);
	   cont.add(cmetric);
	   cont.add(ltwelth);
	   cont.add(ctwelth);
	   ok=new JButton("Enter");
	   ok.setBounds(20,600,70,20);
	   cancel=new JButton("Cancel");
	   cancel.setBounds(120,600,100,20);
	   cont.add(ok);
	   cont.add(cancel);
	   ok.addActionListener(this);
	   cancel.addActionListener(this);

   }//end of constructor

   public void actionPerformed(ActionEvent ae)
    {
		JButton b=(JButton)ae.getSource();
		if(b==ok)
	    {
			String roll,section,name,fname,dob,addr,no,email,gender,ten,twel;
			double rollno=0;
			String dd,mm,yy;
			int i,ch,namelength,tenth,twelth,flag=0;
			try
			{
			roll=troll.getText();
			rollno=Double.parseDouble(roll);
			}
			catch(Exception e)
			{
				flag=1;
				JOptionPane.showMessageDialog(this,"INCORRECT ROLL NO.");
		    }
			section=(String)csection.getSelectedItem();
			section=section.toUpperCase();
			name=tsname.getText().toUpperCase();
			namelength=name.length();
			for(i=0;i<namelength;i++)
			{
				ch=name.charAt(i);
				if(ch>=48 && ch<=57)
				{
					flag=1;
					i=namelength;
					JOptionPane.showMessageDialog(this,"INCORRECT STUDENT NAME");
				}
			}
			fname=tfname.getText().toUpperCase();
			namelength=fname.length();
			for(i=0;i<namelength;i++)
			{
				ch=fname.charAt(i);
				if(ch>=48 && ch<=57)
				{
					flag=1;
					i=namelength;
					JOptionPane.showMessageDialog(this,"FATHER'S NAME INCORRECT");
				}
			}
			no=tphone.getText().toUpperCase();
			namelength=no.length();
			for(i=0;i<namelength;i++)
			{
				ch=no.charAt(i);
				if(ch>=48 && ch<=57)
				{}
				else
				{
					flag=1;
					i=namelength;
					JOptionPane.showMessageDialog(this,"CONTACT NO. INCORRECT");
				}
			}
			dd=(String)c2.getSelectedItem();
			mm=(String)c1.getSelectedItem();
			yy=(String)c3.getSelectedItem();
			dob=dd+"-"+mm+"-"+yy;
			addr=taddr.getText().toUpperCase();
			email=temail.getText().toUpperCase();
			gender=(String)cgender.getSelectedItem();
			gender=gender.toUpperCase();
			ten=(String)cmetric.getSelectedItem();
			twel=(String)ctwelth.getSelectedItem();

		  //check for the missing entries
		  if((name.compareTo("")==0) || (fname.compareTo("")==0) ||  (addr.compareTo("")==0) || (no.compareTo("")==0))
		  {
			  flag=1;
			  JOptionPane.showMessageDialog(this,"FORM INCOMPLETE");
		   }
		   if((email.indexOf('@')==-1) || (email.indexOf('.')==-1))
		   {
			   flag=1;
			   JOptionPane.showMessageDialog(this,"INVALID EMAIL ADDRESS");
		   }
		  if(flag==0)
		  {
		  tenth=Integer.parseInt(ten);
		  twelth=Integer.parseInt(twel);
		  System.out.println(rollno+"\t"+section+"\t"+name+"\t"+fname+"\t"+dob+"\t"+addr+"\t"+no+"\t"+email+"\t"+gender+"\t"+ten+"\t"+twel+"");
		  try
		  {
		    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	        Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
	        batch = esi.getBatch();
	        Statement st=con.createStatement();
	        try
	        {
				ResultSet rs=st.executeQuery("SELECT SECTION FROM STUDENTINFO_"+batch+" WHERE ROLLNO="+rollno);
				rs.next();
				rs.getString(1);
				JOptionPane.showMessageDialog(this,"ROLLNO ALREADY EXISTS");
			}
			catch(Exception e)
			{
  		    PreparedStatement ps=con.prepareStatement("Insert into STUDENTINFO_"+ batch+ "(ROLLNO,SECTION,NAME,FNAME,DOB,ADDR,PNO,EMAIL,GENDER,TENTH,TWELTH) values(?,?,?,?,?,?,?,?,?,?,?)");
	   	    ps.clearParameters();
	   	    ps.setDouble(1,rollno);
	   	    ps.setString(2,section);
	   	    ps.setString(3,name);
	   	    ps.setString(4,fname);
	   	    ps.setString(5,dob);
	   	    ps.setString(6,addr);
	  	  	ps.setString(7,no);
	   	  	ps.setString(8,email);
	        ps.setString(9,gender);
	   	  	ps.setInt(10,tenth);
	   	  	ps.setInt(11,twelth);
	   	  	ps.executeUpdate();
	   	  	con.commit();
	   	  	con.close();
	    	}
		  }//try
		  catch(Exception e)
		  {
			  System.out.println(e);
			  e.printStackTrace();
		  }//catch
		  troll.setText("");
		  tsname.setText("");
		  tfname.setText("");
		  taddr.setText("");
		  tphone.setText("");
		  temail.setText("");
	   	 }//else
	   }//if ok
	   if(b==cancel)
	    {
		  this.setVisible(false);
	    }
	}//actionPerformed
}//end of class

class MaxAttendance extends JFrame implements ActionListener,Constants
{
	JPanel p1,p2;
	JLabel lcno,lattr,lmaxatt;
	TextField tmaxatt;
	//static JComboBox ccno,cattr;
	static Choice ccno,cattr;
	JButton ok,back;
	static int batch, sem,maxatt;
	ImageIcon img;
	JLabel image;
	Container con;

	MaxAttendance(int batch,int sem)
	{
		this.batch=batch;
		this.sem=sem;
		p1=new JPanel();
		lcno=new JLabel("CourseNo");
		lattr=new JLabel("Attribute");
		lmaxatt=new JLabel("Max Attendance");
		tmaxatt=new TextField();

		ccno=new Choice();
		con=getContentPane();
		img=new ImageIcon("logo.jpg");
		image=new JLabel(img);
		image.setBounds(80,120,400,400);
		con.add(image);
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select SUBJECT from SYLLABUS_"+batch+0+sem+"");
			while(rs.next())
			{
				String sub=rs.getString(1);
				ccno.addItem(sub);
				System.out.println(sub);
			}
			rs.close();
			st.close();
			con.close();
		}//try
		catch(Exception e)
		{
			System.out.println("in maxatt: "+e);
			e.printStackTrace();
		}

		cattr=new Choice();
		cattr.addItem("S1ATT");
		cattr.addItem("S2ATT");
		cattr.addItem("S3ATT");

		p1.setLayout(new GridLayout(3,2));
		p1.add(lcno);
		p1.add(ccno);
		p1.add(lattr);
		p1.add(cattr);
		p1.add(lmaxatt);
		p1.add(tmaxatt);
		add(p1,BorderLayout.NORTH);
		p2=new JPanel();
		p2.setLayout(new GridLayout(1,2));
		ok=new JButton("Ok");
		back=new JButton("Back");
		p2.add(ok);
		p2.add(back);
		add(p2,BorderLayout.SOUTH);
		back.addActionListener(this);
		ok.addActionListener(this);
	}//end of constructor

	public void actionPerformed(ActionEvent ae)
	{
		String stri,choice,courseno,course;
		JButton b=(JButton)ae.getSource();
		course=(String)ccno.getSelectedItem();
		choice=(String)cattr.getSelectedItem();
		if(b==ok)
		{
			stri=tmaxatt.getText();
			try
			{
				maxatt=Integer.parseInt(stri);
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(this,"MAX. ATTENDANCE FIELD INCORRECT");
			}
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery("Select COURSENO from SYLLABUS_"+batch+0+sem+" where SUBJECT='"+course+"'" );
				rs.next();System.out.println(choice);
				courseno=rs.getString(1);System.out.println(courseno);
				PreparedStatement ps=con.prepareStatement("UPDATE MAXATTENDANCE_"+batch+0+sem+" SET "+choice+" = ? WHERE COURSENO ='"+courseno+"'");
				ps.clearParameters();
				ps.setInt(1,maxatt);
				ps.executeUpdate();
				tmaxatt.setText("");
				 rs.close();
		  		 st.close();
		  		 con.commit();
				con.close();
			}
			catch(Exception e)
			{
				System.out.print (e);
				e.printStackTrace();
			}
		}
		if(b==back)
		this.setVisible(false);
	}
}

class PasswordChecker extends JFrame implements ActionListener,Constants
{
	JPanel p1,p2;
	JLabel lnick,lpass,lcno,lsec,marksOf;
	TextField tpass;
	static Choice cnick;
	String snick,spass,scno,ssec;
	JButton ok,back;
	static JComboBox attr,csec,csub;
	static int batch, sem;
	static String choice;
	static int flag=0;
	static String section="",course="",subject="",nickname="";
	ImageIcon img;
	JLabel image;
	Container con;
	//constructor
	PasswordChecker(){}
	PasswordChecker(int batch,int sem)
	{
		con=getContentPane();
		img=new ImageIcon("logo.jpg");
		image=new JLabel(img);
		image.setBounds(80,120,400,400);
		con.add(image);
		this.batch=batch;
		this.sem=sem;
		p1=new JPanel();
		lnick=new JLabel("Initials");
		lpass=new JLabel("Password");
		lcno=new JLabel("Course Name");
		lsec=new JLabel("Section");
		cnick=new Choice();
		tpass=new TextField();
		tpass.setEchoChar('*');
		csub=new JComboBox();
		csec=new JComboBox();
		//retrieving filled sections from database and names of the subjects
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select distinct SECTION from STUDENTINFO_"+batch+"");
			while(rs.next())
			{
				String sec=rs.getString(1);
				csec.addItem(sec);
		 	}
		    rs=st.executeQuery("Select SUBJECT from SYLLABUS_"+batch+0+sem+"");
		    while(rs.next())
		    {
				String sub=rs.getString(1);
				csub.addItem(sub);
		 	//  System.out.println(sub);
		    }
		    rs=st.executeQuery("Select INITIALS from LECTURERINFO");
			while(rs.next())
			{
				String nick=rs.getString(1);
				cnick.add(nick);
				// System.out.println(nick);
		    }
		    rs.close();
		    st.close();
		    con.close();
		 }//try
		 catch(Exception e)
		 {
			 System.out.println("in password checker enterssnlmarks: "+e);
		 	 e.printStackTrace();
		 }
		 marksOf=new JLabel("Attribute to enter marks");
		 attr=new JComboBox();
		 attr.addItem("S1");
		 attr.addItem("S2");
		 attr.addItem("S3");
		 attr.addItem("A1");
		 attr.addItem("A2");
		 attr.addItem("A3");
		 attr.addItem("A4");
		 attr.addItem("A5");
		 attr.addItem("S1ATT");
		 attr.addItem("S2ATT");
		 attr.addItem("S3ATT");
		 attr.addItem("Benefit");
  		 p1.setLayout(new GridLayout(7,2));
		 p1.add(lnick);
		 p1.add(cnick);
		 p1.add(lpass);
		 p1.add(tpass);
		 p1.add(lcno);
		 p1.add(csub);
		 p1.add(lsec);
		 p1.add(csec);
		 p1.add(marksOf);
		 p1.add(attr);
		 JLabel s=new JLabel("S..->Sessionals");
		 JLabel a=new JLabel("A..->Assignments");
		 JLabel at=new JLabel("Att..->Attendance");
		 p1.add(s);
		 p1.add(a);
		 p1.add(at);
		 add(p1,BorderLayout.NORTH);
		 p2=new JPanel();
		 p2.setLayout(new GridLayout(1,2));
		 ok=new JButton("Ok");
		 back=new JButton("Back");
		 p2.add(ok);
		 p2.add(back);
		 add(p2,BorderLayout.SOUTH);
		 back.addActionListener(this);
		 ok.addActionListener(this);
	}//end of constructor
	String getSection()
	{
		return(section);
	}
	String getCourse()
	{
		try
		{
	   		subject=(String) csub.getSelectedItem();
	   		//System.out.println(subject);
	   		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	   		Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
	   		//System.out.println("hello");
	   		Statement st=con.createStatement();
	   		String q="Select COURSENO from SYLLABUS_"+batch+0+sem+" where SUBJECT='"+subject+"'";
	   											//System.out.println(q);
	   		ResultSet rs=st.executeQuery(q);
	   		rs.next();
	   		course=rs.getString(1);
	   											//System.out.println(course);
	   		rs.close();
	   		st.close();
	   		con.close();
	   	  }
	   	 catch(Exception e)
	   	  {
	   		 System.out.println("in password checker enterssnlmarks :getCourse() :"+e);
	   		 //e.printStackTrace();
	   	  }
	   	return(course);
	}
	void setFlag()
	{
	      flag=1;
	}
	public void actionPerformed(ActionEvent ae)
    {
		 int RecordType=0;
		 JButton b=(JButton)ae.getSource();
		 TableCreater tc = new TableCreater();
		 nickname=snick=(String)cnick.getSelectedItem();
		 spass=tpass.getText().toUpperCase();
		 course=scno=this.getCourse();
		 section=ssec=(String)csec.getSelectedItem();
		 										//System.out.println(section);
		 choice=(String)attr.getSelectedItem();
		 										//System.out.println(choice);
		 if(b==back)
		  this.setVisible(false);

		 if(b==ok)
		  {
			//check for the missing entry
			if((snick.compareTo("")==0) || (spass.compareTo("")==0) || (scno.compareTo("")==0) || (ssec.compareTo("")==0))
			 {
			   JOptionPane.showMessageDialog(this,"DETAILS INCOMPLETE");
		     }
    	    else
    	     {
			  try
    	       {
				 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				 Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
				 Statement st=con.createStatement();
				 ResultSet rs=st.executeQuery("SELECT PASSWORD FROM LECTURERINFO WHERE INITIALS='"+snick+"'");
				 rs.next();
				 String str=rs.getString(1);	//System.out.println("hello");
												// System.out.println(str);
				 if(spass.compareTo(str)==0)
				 {
					 if((snick.compareTo("CC1")==0) || (snick.compareTo("HOD")==0) || (snick.compareTo("CC2")==0) || (snick.compareTo("CC3")==0) || (snick.compareTo("CC4")==0))
					 {
						 RecordType=SSNL;
						 tc.TableChecker(RecordType);
						 //System.out.println("byebye");
						 System.out.println(batch+","+sem+","+choice);
						 EnterSsnlInfo esni=new EnterSsnlInfo(batch,sem,choice);
						 esni.setTitle("Academic Record");
						 esni.setSize(900,600);
						 if(flag==0)
						 esni.setVisible(true);
						 flag=0;
					 }
					 else
					 {

						 ResultSet rs1=st.executeQuery("SELECT "+section+" FROM LECTCHART_"+batch+0+sem+" WHERE COURSENO='"+course+"'");
						 rs1.next();
						 String str1=rs1.getString(1);		System.out.println(str1);
						 if(snick.compareTo(str1)==0)
						 {
							 RecordType=SSNL;
							 tc.TableChecker(RecordType);
							 //System.out.println("byebye");
							 System.out.println(batch+","+sem+","+choice);
							 EnterSsnlInfo esni=new EnterSsnlInfo(batch,sem,choice);
							 esni.setTitle("Academic Record");
							 esni.setSize(900,600);
							 if(flag==0)
							 esni.setVisible(true);
							 flag=0;
						 }
						 else
						 {
							 JOptionPane.showMessageDialog(this,"INCORRECT INITIALS OR SUBJECT OR SECTION");
				 			}
					}
				}
				 else
				 {
					 JOptionPane.showMessageDialog(this,"INCORRECT PASSWORD ");
				 }
			   }
			   catch(Exception e)
			   {
			 	 System.out.print (e);
			 	 e.printStackTrace();
			   }
			 }//else

		 }//if ok
	   }//actionPerformed
   }//class PasswordChecker

class EnterSsnlInfo extends JFrame implements ActionListener
{
      static int flag=0, maxatt, batch,sem;
      int count=0,bflag=0;
      JOptionPane jop = new JOptionPane();
      PasswordChecker pc = new PasswordChecker();
      JPanel p;
      JButton enter,back;
      JTable tbl;
      static String sec,choice,course;
        //constructor
        EnterSsnlInfo(){}
        EnterSsnlInfo(int batch,int sem,String choice)
        {
   		this.batch=batch;
   		this.sem=sem;
   		this.choice=choice;
   		try
   		{
   		 sec=pc.getSection();
   		 course=pc.getCourse();
	    }
	    catch(Exception e)
	     {
		  System.out.println("After getcourse() and getSection() called:"+e);
		  e.printStackTrace();
	     }
   		 if(choice.compareTo("Benefit")==0)
   		  {
   		   bflag=1;
   		   tbl=new JTable(30,15);
   		   tbl.setValueAt("RollNo",0,0);
   		   tbl.setValueAt("S1ATT",0,1);
   		   tbl.setValueAt("S2ATT",0,2);
   		   tbl.setValueAt("S3ATT",0,3);
   		   tbl.setValueAt("S1",0,4);
   		   tbl.setValueAt("S2",0,5);
   		   tbl.setValueAt("S3",0,6);
   		   tbl.setValueAt("A1",0,7);
   		   tbl.setValueAt("A2",0,8);
   		   tbl.setValueAt("A3",0,9);
   		   tbl.setValueAt("A4",0,10);
   		   tbl.setValueAt("A5",0,11);
   		   tbl.setValueAt("TOTAL",0,12);
   		   tbl.setValueAt("BENEFIT",0,13);
   		   tbl.setValueAt("FINAL MARKS",0,14);
   	      }
   		   else
   		  {
			  tbl=new JTable(30,3);
			  tbl.setValueAt("ROLLNO",0,0);
			  tbl.setValueAt("NAME",0,1);
			  tbl.setValueAt(choice,0,2);
   		  }
   		  ExcelAdapter myAd=new ExcelAdapter(tbl);
   			if(flag==1)
   			 {
				 fillRollno();
				 flag=0;
			 }
   			 getData();
   		 this.add(tbl,BorderLayout.CENTER);
   		 JPanel p=new JPanel();
   		 enter=new JButton("Enter");
   		 back=new JButton("Back");
   		 p.add(enter);
   		 p.add(back);
   		 enter.addActionListener(this);
   		 back.addActionListener(this);
   		 add(p,BorderLayout.SOUTH);
   		 tbl.addKeyListener(new KeyAdapter()
   		 	                  {
   		 						public void keyPressed(KeyEvent ke)
   		                         {
   		 						   int k=ke.getKeyCode();
   		 						   String str="";


   		     					   switch(k)
   		 						   {
   		 							  case KeyEvent.VK_ENTER:
   										{
											str=(String)tbl.getValueAt(1,13);
   		 							      for(int i=2;i<=count;i++)
   		 							      {
   		 							        tbl.setValueAt(str,i,13);
   		 							      }
   		 							  }
   		 							  break;
   		 						   }//switch
   		 					    }//keyPressed
   					 });//keyListener
   	  }//end of constructor

   	  void fillRollno()
   	  {
   			Connection con;
   			double rollno;
   			try
   			{
   				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
   				con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
   				Statement st=con.createStatement();
   				ResultSet rs;
   				rs=st.executeQuery("select ROLLNO from STUDENTINFO_"+batch);
   				while(rs.next())
   				{
   					rollno=rs.getDouble(1);
   					PreparedStatement ps=con.prepareStatement("Insert into SSNL_"+course+"_"+batch+0+sem+ "(ROLLNO) values(?)");
   					ps.clearParameters();
   					ps.setDouble(1,rollno);
   					ps.executeUpdate();
   					con.commit();
   				}
   				st.close();
   				con.close();
   			}
   			catch(ClassNotFoundException e)
   			{
   				System.out.print (e);
   			}
   			catch(SQLException e)
   			{
   				System.out.print (e);
   			}
   		}
   	void setFlag()
   	{
   		flag=1;
   	}
   	 void getData()
   	 {
   		 Connection con;
   		 int row=1;
   		 try
   		 {
   			 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
   			 con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
   			 Statement st=con.createStatement();
   			 ResultSet rs;
   			 if(bflag==1)
   			 {
   				 rs=st.executeQuery("Select * from SSNL_"+course+"_"+batch+0+sem+" WHERE ROLLNO IN (SELECT ROLLNO FROM STUDENTINFO_"+batch+" WHERE SECTION='"+sec+"')");
   				 while(rs.next())
   				 {
   					 count++;
   					 tbl.setValueAt(rs.getString(1),row,0);
   					 tbl.setValueAt(rs.getString(2),row,1);
   					 tbl.setValueAt(rs.getInt(3),row,2);
   					 tbl.setValueAt(rs.getInt(4),row,3);
   					 tbl.setValueAt(rs.getInt(5),row,4);
   					 tbl.setValueAt(rs.getInt(6),row,5);
   					 tbl.setValueAt(rs.getInt(7),row,6);
   					 tbl.setValueAt(rs.getInt(8),row,7);
   					 tbl.setValueAt(rs.getInt(9),row,8);
   					 tbl.setValueAt(rs.getInt(10),row,9);
   					 tbl.setValueAt(rs.getInt(11),row,10);
   					 tbl.setValueAt(rs.getInt(12),row,11);
   					 tbl.setValueAt(rs.getInt(13),row,12);
   					 tbl.setValueAt(rs.getInt(14),row,13);
   					 tbl.setValueAt(rs.getInt(15),row,14);
   					 row=row+1;
   				 }//while
   				 rs.close();
   				 st.close();
   				 con.close();
   			 }
   			 else
   			 {
				 String data="";
				 rs=st.executeQuery("Select "+choice+" from SSNL_"+course+"_"+batch+0+sem+" WHERE ROLLNO IN (SELECT ROLLNO FROM STUDENTINFO_"+batch+" WHERE SECTION='"+sec+"')");
				 rs.next();
				 data=rs.getString(1);
				 if(data==null)
				 {
					 rs=st.executeQuery("select ROLLNO,NAME from STUDENTINFO_"+batch+" WHERE SECTION='"+sec+"'");
					 while(rs.next())
					 {
						 count++;
						 tbl.setValueAt(rs.getDouble(1),row,0);
						 tbl.setValueAt(rs.getString(2),row,1);
						 row++;
					 }
				 }
				 else
				 {
					 pc.setFlag();
					 JOptionPane.showMessageDialog(this,"RECORD ALREADY EXIST");
				 }
				 st.close();
				 con.close();
			 }
		 }
		 catch(Exception e)
   		 {
   			 System.out.print ("in class enter ssnl:"+e);
   		 }
   	 }
   	 public void actionPerformed(ActionEvent ae)
   	  {
		  String stri;
   		  JButton b=(JButton)ae.getSource();
   		  if(b==back)
   		  this.setVisible(false);
   		  if(b==enter)
   		  {
   			  int i=0;
   			  try
   			  {
   				  String str,ttl,benefit;
   				  double  rollno;
   				  int ttlssnl;
   				  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
   				  Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
   				  Statement st=con.createStatement();
   				  if(bflag==1)
   				  {
					  int a,c,d;
					  for(i=1;i<=count;i++)
					  {
						  String roll=(String)tbl.getValueAt(i,0);
						  rollno=Double.parseDouble(roll);
						  ttl=(String)tbl.getValueAt(i,12);System.out.println("KK");
						  benefit=(String)tbl.getValueAt(i,13);System.out.println("KK11");
						  if((ttl.compareTo("")==0) || (benefit.compareTo("")==0))
						  {
							  JOptionPane.showMessageDialog(this,"DETAILS INCOMPLETE");
							  break;
						  }
						  else
						  {
							  try
							  {
								  a=Integer.parseInt(ttl);System.out.println("11111bdbdbd");
								  c=Integer.parseInt(benefit);
							  }
							  catch(Exception e)
							  {
								  JOptionPane.showMessageDialog(this,"TOTAL OR BENEFIT FIELD CONTAINS TEXT");
								  break;
							  }
							  ttlssnl=a+c;
							  if(ttlssnl>=50)
							  ttlssnl=49;
							  PreparedStatement ps=con.prepareStatement("UPDATE SSNL_"+course+"_"+batch+0+sem+" SET TTL = ? WHERE ROLLNO ="+rollno+"");
							  ps.clearParameters();
							  ps.setInt(1,a);
							  ps.executeUpdate();
							  ps=con.prepareStatement("UPDATE SSNL_"+course+"_"+batch+0+sem+" SET BENEFIT = ? WHERE ROLLNO ="+rollno+"");
							  ps.clearParameters();
							  ps.setInt(1,c);
							  ps.executeUpdate();
							  ps=con.prepareStatement("UPDATE SSNL_"+course+"_"+batch+0+sem+" SET TTLSSNL = ? WHERE ROLLNO ="+rollno+"");
							  ps.clearParameters();
							  ps.setInt(1,ttlssnl);
							  ps.executeUpdate();
						  }
					  }
					  if(i==count+1)
					  this.setVisible(false);
					  con.commit();
					  con.close();
				  }
				  else
				  {
					  int value;
					  for(i=1;i<=count;i++)
					  {
						  rollno=(Double)tbl.getValueAt(i,0);
						  str=(String)tbl.getValueAt(i,2);
						  if((str.compareTo("")==0))
						  {
							  JOptionPane.showMessageDialog(this,"DETAILS INCOMPLETE");
							  break;
						  }
						  else
						  {
							  try
							  {
								  value=Integer.parseInt(str);
							  }
							  catch(Exception e)
							  {
								  JOptionPane.showMessageDialog(this,"FIELD CONTAINS TEXT");
								  break;
							  }
							  PreparedStatement ps=con.prepareStatement("UPDATE SSNL_"+course+"_"+batch+0+sem+" SET "+choice+" = ? WHERE ROLLNO ="+rollno+"");
							  ps.clearParameters();
							  ps.setInt(1,value);
							  ps.executeUpdate();
						  }
					  }
					  if(i==count+1)
					  this.setVisible(false);
					  con.commit();
					  con.close();
				  }
			  }//try
			  catch(Exception e)
   			  {
				  e.printStackTrace();
   				  System.out.println(e);
   			  }//catch
   		  }
   	  }//actionPerformed
}//class EnterSsnlInfo



class LectChart extends JFrame implements ActionListener ,Constants
{
  JPanel p;
  static int flag=0,nsem;
  JButton back,enter;
  JTable tbl;
  static String batch,sem;
  int row=1;
  int col=2;
  int RecordType=0;
  TableCreater tc=new TableCreater();

    //constructor
  LectChart(){}
  LectChart(String batch,String sem)
  {
	  this.batch=batch;
	  this.sem=sem;
	  nsem=Integer.parseInt(sem);
	  tbl=new JTable(13,8);
	  tbl.setCellSelectionEnabled(true);
	  tbl.setLayout(new BorderLayout());
	  ExcelAdapter myAd = new ExcelAdapter(tbl);
	  tbl.setValueAt("Course No",0,0);
	  tbl.setValueAt("Subject",0,1);
	  tbl.setValueAt("A1",0,2);
	  tbl.setValueAt("A2",0,3);
	  tbl.setValueAt("A3",0,4);
	  tbl.setValueAt("B1",0,5);
	  tbl.setValueAt("B2",0,6);
	  tbl.setValueAt("B3",0,7);
	  this.add(tbl,BorderLayout.CENTER);
	  p=new JPanel();
	  p.setLayout(new GridLayout(1,2));
	  enter=new JButton("Enter");
	  back=new JButton("Back");
	  enter.addActionListener(this);
	  back.addActionListener(this);
	  p.add(enter);
	  p.add(back);
	  add(p,BorderLayout.SOUTH);
	   try
	   {
	     getOldTable();     //retrieving the Subject code and name from SYLLABUSINFO table
	   }					//& retreiving previous chart if present
	   catch(Exception e)
	   {
		  System.out.println(e);
		  e.printStackTrace();
	   }

	    tbl.addKeyListener(new KeyAdapter()
	                  {
						public void keyPressed(KeyEvent ke)
                        {
						   int k=ke.getKeyCode();
						   String str="";
						   row=tbl.getEditingRow();
						   col=tbl.getEditingColumn();

						   if(row>0 && col>0)
						   {
							   str=(String)tbl.getValueAt(row,col-1);
						   }
    					   switch(k)
						   {
							  case KeyEvent.VK_TAB:
							  if(row>0 && col>0)
							  {
							      for(int i=col;i<=7;i++)
							      {
							        tbl.setValueAt(str,row,i);
							      }
							  }
							  break;
						   }//switch
					    }//keyPressed
					 });//keyListener
     }//end of constructor
 void setFlag()
 {
	 flag=1;
 }

 public void getOldTable() throws Exception
 {
	 String table ="SYLLABUS_"+batch+0+sem;

	 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	 Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
	 Statement st=con.createStatement();
	 ResultSet rs;
	 rs=st.executeQuery("Select COURSENO,SUBJECT from "+ table +"");
	 row=1;
	 col=0;
	 while(rs.next())
	 {
		 tbl.setValueAt(rs.getString(1),row,col);
		 tbl.setValueAt(rs.getString(2),row,col+1);
		 row++;
	 }
	 row=1;col=2;
	 if(flag==1)
	 {
		 System.out.println("inif");
		 table="LECTCHART_"+batch+0+sem;
		 int row=1;
		 rs=st.executeQuery("Select A1,A2,A3,B1,B2,B3 from "+ table+"");System.out.println("inif");
		 //System.out.println("table found");
		 while(rs.next())
		 {
			 //tbl.setValueAt(rs.getString(1),row,0);
			 tbl.setValueAt(rs.getString("A1"),row,2);
			 tbl.setValueAt(rs.getString("A2"),row,3);
			 tbl.setValueAt(rs.getString("A3"),row,4);
			 tbl.setValueAt(rs.getString("B1"),row,5);
			 tbl.setValueAt(rs.getString("B2"),row,6);
			 tbl.setValueAt(rs.getString("B3"),row,7);
			 row=row+1;
		 }//while
	 }
	 rs.close();
	 st.close();
	 con.close();
	 row=1;col=2;
 }
 public void actionPerformed(ActionEvent ae)
 {
	 JButton b=(JButton)ae.getSource();
	 if(b==enter)
	 {

		 int i=1,j=1;
		 String csno[]= new String[12];
		 col=0;
		 try
		 {
			 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			 Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
			 Statement st=con.createStatement();
			 if (flag==1)
			 {
				 st.executeUpdate("DROP TABLE "+ "LECTCHART_"+batch+0+sem);
				 RecordType = LECTURERCHART;
				 tc.TableChecker(RecordType);
			 }
			 String courseno,a1,a2,a3,b1,b2,b3;
			 j=0;
			 while(csno[j]!=null)
			 {
				 System.out.println("inwhile");
				 csno[j]="";
				 j++;
			 }
			 for(i=1;i<(NOSEMCOURSE[nsem-1])+1;i++)
			 {
				 courseno=(String)tbl.getValueAt(i,col);
				 courseno=courseno.toUpperCase();
				 csno[i]=courseno;
				 j=1;
				 col=col+1;
				 a1=(String)tbl.getValueAt(i,++col);
				 a1=a1.toUpperCase();
				 a2=(String)tbl.getValueAt(i,++col);
				 a2=a2.toUpperCase();
				 a3=(String)tbl.getValueAt(i,++col);
				 a3=a3.toUpperCase();
				 b1=(String)tbl.getValueAt(i,++col);
				 b1=b1.toUpperCase();
				 b2=(String)tbl.getValueAt(i,++col);
				 b2=b2.toUpperCase();
				 b3=(String)tbl.getValueAt(i,++col);
				 b3=b3.toUpperCase();
				 col=0;
				 if((courseno.compareTo("")==0)|| (a1.compareTo("")==0) || (a2.compareTo("")==0) || (a3.compareTo("")==0) || (b1.compareTo("")==0)|| (b2.compareTo("")==0) || (b3.compareTo("")==0))
				 {
					 JOptionPane.showMessageDialog(this,"DETAILS INCOMPLETE");
					 i=(NOSEMCOURSE[nsem-1])+3;
		         }
				 if(csno[j]!=null)
				 {
					 while(j<i)
					 {
						 if(csno[j].contentEquals(courseno ))
						 {
							 JOptionPane.showMessageDialog(this,"COURSENO ALREADY ALLOCATED");
							 i=(NOSEMCOURSE[nsem-1])+3;
							 break;
						 }
						 else
						 {
							 j++;
						 }
					 }
				 }
		         if(i<(NOSEMCOURSE[nsem-1])+1)
		         {
					 PreparedStatement ps=con.prepareStatement("Insert into LECTCHART_"+ batch+0+sem+ "(COURSENO,A1,A2,A3,B1,B2,B3) values(?,?,?,?,?,?,?)");
					 ps.clearParameters();
					 ps.setString(1,courseno);
					 ps.setString(2,a1);
					 ps.setString(3,a2);
					 ps.setString(4,a3);
					 ps.setString(5,b1);
					 ps.setString(6,b2);
					 ps.setString(7,b3);
					 ps.executeUpdate();
					 courseno="";a1="";a2="";a3="";b1="";b2="";b3="";
				 }
			 }
			 con.commit();
			 con.close();
			 row=1;col=2;
		 }//try
		 catch(Exception e)
		 {
			 System.out.println(e);
			 e.printStackTrace();
		 }//catch
		 if(i==(NOSEMCOURSE[nsem-1])+1)
		 this.setVisible(false);
	 }
	 if(b==back)
	 {
		 this.setVisible(false);
	 }
   }//action Performed
 }//class LectChart


//This class facilitates the entry of syllabus
class EnterSylab extends JFrame implements ActionListener,Constants
{
   static int sem,batch,row;
   static int flag =0;
   JPanel p;
   JTable tbl;
   JButton enter,back;
   //constructor
   EnterSylab(){}
   EnterSylab(int batch,int sem)
   {

	  this.batch=batch;
	  this.sem=sem;
	  JPanel p=new JPanel();
	  enter=new JButton("Enter");
	  back=new JButton("Back");
	  p.add(enter);
	  p.add(back);
	  p.setLayout(new GridLayout(2,2));
	  JLabel note = new JLabel("Don't Enter More Than 10 Characters in FieldValue");
	  p.add(note);
	  back.addActionListener(this);enter.addActionListener(this);
	  add(p,BorderLayout.SOUTH);
	  tbl=new JTable(13,9);
	  tbl.setValueAt("Course No.",0,0);
	  tbl.setValueAt("Subject",0,1);
	  tbl.setValueAt("L",0,2);
	  tbl.setValueAt("T",0,3);
	  tbl.setValueAt("P",0,4);
	  tbl.setValueAt("Theory",0,5);
	  tbl.setValueAt("Sessional",0,6);
	  tbl.setValueAt("Practical",0,7);
	  tbl.setValueAt("Total",0,8);
	  this.add(tbl,BorderLayout.CENTER);
	  ExcelAdapter el=new ExcelAdapter(tbl);

	  try
	  {
		  getOldTable();
	  }
	  catch(Exception e)
	  {
		  JOptionPane.showMessageDialog(this,"NO PREVIOUS SYLLABUS PRESENT");

	  }

    }//end of constructor

    public void getOldTable() throws Exception
    {

		String table ="SYLLABUS_"+(batch-1)+0+sem;

		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
		Statement st=con.createStatement();
		ResultSet rs;
		rs=st.executeQuery("Select * from "+ table);
		row=1;
		while(rs.next())
		{
			tbl.setValueAt(rs.getString(1),row,0);
			tbl.setValueAt(rs.getString(2),row,1);
			tbl.setValueAt(rs.getString(3),row,2);
			tbl.setValueAt(rs.getString(4),row,3);
			tbl.setValueAt(rs.getString(5),row,4);
			tbl.setValueAt(rs.getString(6),row,5);
			tbl.setValueAt(rs.getString(7),row,6);
			tbl.setValueAt(rs.getString(8),row,7);
			tbl.setValueAt(rs.getString(9),row,8);
			row=row+1;
		}//while
		rs.close();
		st.close();
		con.close();
		row=1;
	}
	void setFlag()
	{
		flag=1;
	}

    public void actionPerformed(ActionEvent ae)
    {
		int col,row;
		JButton b=(JButton)ae.getSource();
		if(b==enter)
		{
			int i=1;
			col=0;
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
				String courseno,subject,l1,t1,p1,theory1,ssnl1,practical1,total1;
				int j=1,l=0,t=0,p=0,theory=0,ssnl=0,practical=0,total=0,eflag=0;
				String csno[]= new String[12];
				Statement st=con.createStatement();

				j=0;
				while(csno[j]!=null)
				{
					System.out.println("inwhile");
					csno[j]="";
					j++;
				}
				for(i=1;i<(NOSEMCOURSE[sem-1])+1;i++)
				{
					courseno=(String)tbl.getValueAt(i,col);
					subject=(String)tbl.getValueAt(i,++col);
					if((courseno.compareTo("")==0)|| (subject.compareTo("")==0))
					{
						JOptionPane.showMessageDialog(this,"TEXT FIELD EMPTY");
						i=(NOSEMCOURSE[sem-1]+3);
					}
					courseno=courseno.toUpperCase();
					int h=courseno.indexOf('-');
					if(h>0)
					{
						int len=courseno.length();
						String str=courseno.substring(0,h);
						String str1=courseno.substring(h+1,len);
						courseno=str+str1;
					}
					csno[i]=courseno;
					j=1;
					subject=subject.toUpperCase();
					if(csno[j]!=null)
					{
						while(j<i)
						{
							if(csno[j].contentEquals(courseno ))
							{
								JOptionPane.showMessageDialog(this,"COURSENO ALREADY ALLOCATED");
								i=(NOSEMCOURSE[sem-1])+3;
								break;
							}
							else
							{
								j++;
							}
						}
					 }
					try
					{
					l1=(String)tbl.getValueAt(i,++col);
					l=Integer.parseInt(l1);
					t1=(String)tbl.getValueAt(i,++col);
					t=Integer.parseInt(t1);
					p1=(String)tbl.getValueAt(i,++col);
					p=Integer.parseInt(p1);
					theory1=(String)tbl.getValueAt(i,++col);
					theory=Integer.parseInt(theory1);
					ssnl1=(String)tbl.getValueAt(i,++col);
					ssnl=Integer.parseInt(ssnl1);
					practical1=(String)tbl.getValueAt(i,++col);
					practical=Integer.parseInt(practical1);
					total1=(String)tbl.getValueAt(i,++col);
					total=Integer.parseInt(total1);
					col=0;
				     }
				     catch(Exception e)
				     {

						 JOptionPane.showMessageDialog(this,"NUMBER FIELD INCORRECT");
						 i=(NOSEMCOURSE[sem-1]+3);
					 }
					if(i<(NOSEMCOURSE[sem-1])+1)
					{
						PreparedStatement ps=con.prepareStatement("Insert into MAXATTENDANCE_"+batch+0+sem+" (COURSENO) values(?)");
						ps.clearParameters();
						ps.setString(1,courseno);
						ps.executeUpdate();
						ps=con.prepareStatement("Insert into SYLLABUS_"+ batch+0+sem+ "(COURSENO,SUBJECT,L,T,P,THEORY,SSNL,PRACTICAL,TOTAL) values(?,?,?,?,?,?,?,?,?)");
						ps.clearParameters();
						ps.setString(1,courseno);
						ps.setString(2,subject);
						ps.setInt(3,l);
						ps.setInt(4,t);
						ps.setInt(5,p);
						ps.setInt(6,theory);
						ps.setInt(7,ssnl);
						ps.setInt(8,practical);
						ps.setInt(9,total);
						ps.executeUpdate();
					}
				}
				con.commit();
				con.close();
				row=1;col=2;
			}//try
			catch(Exception e)
			{
				System.out.println(e);
				e.printStackTrace();
			}//catch
			if(i==(NOSEMCOURSE[sem-1])+1)
			this.setVisible(false);
		}
	    if(b==back)
	    this.setVisible(false);
	 }//actionPerformed
 }//class EnterSylab

class EnterLectInfo extends JFrame implements ActionListener
{
    static int flag=0;
    int row;
	BorderLayout br=new BorderLayout();
	JTable jt;
	JPanel p;
	JButton edit,back;
	 //constructor
	 EnterLectInfo()
     {
		 super();
	     try
	     {
		    jbInit();
		 }
		 catch (Exception e)
		 {
		     e.printStackTrace();
		 }
		 p=new JPanel();
		 p.setLayout(new GridLayout(1,2));
		 edit=new JButton("Edit");
		 back=new JButton("Back");
		 p.add(edit);
		 p.add(back);
		 this.add(jt,BorderLayout.CENTER);
		 this.add(p,BorderLayout.SOUTH);
	     edit.addActionListener(this);
	     back.addActionListener(this);
	     if(flag==1){
	     try
	     {
			 getOldTable();
		 }
		 catch(Exception e)
		 {
			 System.out.println("Inside lectinfo:"+e);
			 e.printStackTrace();
		 }         }
	 }//end of constructor
	 public void getOldTable() throws Exception
	 {
		 String table ="LECTURERINFO";

		 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
		 Statement st=con.createStatement();
		 ResultSet rs;
		 rs=st.executeQuery("Select * from "+ table);
		 row=1;
		 while(rs.next())
		 {
			 jt.setValueAt(rs.getString(1),row,0);
			 jt.setValueAt(rs.getString(2),row,1);

			 row=row+1;
		 }//while
		 rs.close();
		 st.close();
		 con.close();
		 row=1;
	 }
	 void setFlag()
	 {
		 flag=1;
	 }
	 public void jbInit() throws Exception
	 {
	  jt=new JTable(30,2);
	  jt.setCellSelectionEnabled(true);
	  jt.setLayout(br);
	  jt.setValueAt("Name",0,0);
	  jt.setValueAt("Initials",0,1);
     }//jbInit
     public void actionPerformed(ActionEvent ae)
     {
		 int row,col,count=0;
		 JButton b=(JButton)ae.getSource();
		 if(b==edit)
		 {
			 EditLectInfo edli=new EditLectInfo();
			 edli.setTitle("Edit Lecturer Information");
			 edli.setSize(400,200);
			 edli.setVisible(true);
		 }
		 if(b==back)
	     this.setVisible(false);
	 }//actionPerformed
}//class EnterLectInfo

class EditLectInfo extends JFrame implements ActionListener
{
	JPanel p1,p2;
	JLabel lnick,lname,lpass, lcpass;
	TextField tnick, tname, tpass, tcpass;
	static String intl,name,passwd,cpasswd,str;
	static int flag=0;
	JButton add, delete, back;

	//constructor
	EditLectInfo()
	{
		 p1=new JPanel();
		lnick=new JLabel("Initials");
		lname=new JLabel("Name");
		lpass=new JLabel("Password");
		lcpass=new JLabel("Confirm Password");
		tnick=new TextField();
		tname=new TextField();
		tpass=new TextField();
		tpass.setEchoChar('*');
		tcpass=new TextField();
		tcpass.setEchoChar('*');
		p1.setLayout(new GridLayout(4,2));
		p1.add(lnick);
		p1.add(tnick);
		p1.add(lname);
		p1.add(tname);
		p1.add(lpass);
		p1.add(tpass);
		p1.add(lcpass);
		p1.add(tcpass);
		add(p1,BorderLayout.NORTH);
		p2=new JPanel();
		p2.setLayout(new GridLayout(1,3));
		add=new JButton("Add");
		delete=new JButton("Delete");
		back=new JButton("Back");
		p2.add(add);
		p2.add(delete);
		p2.add(back);
		add(p2,BorderLayout.SOUTH);
		back.addActionListener(this);
		add.addActionListener(this);
		delete.addActionListener(this);
	}//end of constructor

	public void actionPerformed(ActionEvent ae)
	{
		flag=0;
		JButton b=(JButton)ae.getSource();
		intl=tnick.getText().toUpperCase();
		name=tname.getText().toUpperCase();
		passwd=tpass.getText().toUpperCase();
		cpasswd=tcpass.getText().toUpperCase();
		if(b==add)
		{
			//check for the missing entry
			if((intl.compareTo("")==0) || (name.compareTo("")==0)  || (passwd.compareTo("")==0) || (cpasswd.compareTo("")==0))
			{
				flag=1;
				JOptionPane.showMessageDialog(this,"DETAILS INCOMPLETE");
			}
			else
			{
				if(passwd.contentEquals(cpasswd))
				{}
				else
				{
					flag=1;
					JOptionPane.showMessageDialog(this,"PASSWORD FIELDS DOESN'T MATCH");
			    }
			}
			if(flag==0)
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery("SELECT NAME FROM LECTURERINFO WHERE INITIALS='"+intl+"'");
					rs.next();
					str=rs.getString(1);
					JOptionPane.showMessageDialog(this,"INITIAL ALREADY ALLOTED TO "+str);
				}
				catch(Exception e)
				{
					try
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
						PreparedStatement ps=con.prepareStatement("Insert into LECTURERINFO"+" (NAME,INITIALS,PASSWORD) values(?,?,?)");
						ps.clearParameters();
						ps.setString(1,name);
						ps.setString(2,intl);
						ps.setString(3,passwd);
						ps.executeUpdate();
						con.commit();
						con.close();
					}
					catch(Exception ex)
					{}
					name="";intl="";passwd="";
					tname.setText("");
					tnick.setText("");
					tpass.setText("");
					tcpass.setText("");
				}
			}
		}//add
		if(b==delete)
		{
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery("SELECT PASSWORD FROM LECTURERINFO WHERE INITIALS='"+intl+"'");
				rs.next();
				str=rs.getString(1);
				if(str.contentEquals(passwd))
				{
					st.executeUpdate("DELETE FROM LECTURERINFO WHERE INITIALS='"+intl+"'");
					con.commit();
					con.close();
					tname.setText("");
					tnick.setText("");
					tpass.setText("");
					tcpass.setText("");
				}
				else
				{
					JOptionPane.showMessageDialog(this,"PASSWORD WRONG ");
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(this,"INITIAL NOT PRESENT");
			}
		}
		if(b==back)
		this.setVisible(false);
	}//action performed
}//EditLectInfo class

//This class creates a new table if it doesnt exists
class TableCreater extends JFrame implements Constants
  {
 	 static String sbatch="",ssem="",course="";
	 EnterSsnlInfo esni= new EnterSsnlInfo();
 	 PasswordChecker pc = new PasswordChecker();
 	 EnterStudInfo esi=new EnterStudInfo();
 	 EnterSylab es=new EnterSylab();
 	 EnterLectInfo eli=new EnterLectInfo();


 	 void TableChecker(int RecordType)
 	 {
		 sbatch= esi.getBatch();
		 ssem= esi.getSem();



		 try
		 {
			 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			 Connection con=DriverManager.getConnection("jdbc:odbc:ADIMS","scott","tiger");
			 Statement st=con.createStatement();
			 if(RecordType==STUDENTINFO)
			 {
				 try
				 {
					 st.executeQuery("SELECT GENDER FROM STUDENTINFO_"+	sbatch);
				 }
				 catch(SQLException e)
				 {
					 st.executeUpdate("CREATE TABLE  STUDENTINFO_"+ sbatch + "(ROLLNO NUMBER(10), NAME VARCHAR(50),SECTION VARCHAR(3) ,FNAME VARCHAR(50) ,DOB VARCHAR(12) ,ADDR VARCHAR(100) , PNO VARCHAR(15),EMAIL VARCHAR(20),GENDER VARCHAR(6) ,TENTH NUMBER(2) ,TWELTH NUMBER(2) ,PRIMARY KEY(ROLLNO))");
				 }
				 st.close();
				 con.close();
				 return;
			 }
			 else if(RecordType==LECTURERCHART)
			 {
				 try
				 {
					 st.executeQuery("SELECT COURSENO FROM LECTCHART_"+ sbatch+0+ssem);
					 LectChart lc = new LectChart();
					 lc.setFlag();
				 }
				 catch(SQLException e)
				 {

					 st.executeUpdate("CREATE TABLE LECTCHART_"+sbatch+0+ssem+
					 "(COURSENO VARCHAR(10) PRIMARY KEY, A1 VARCHAR(3) ,"+
					 "A2 VARCHAR(3) ,A3 VARCHAR(3) ,B1 VARCHAR(3) ,"+
					 "B2 VARCHAR(3),B3 VARCHAR(3),FOREIGN KEY(COURSENO) REFERENCES SYLLABUS_"+sbatch+0+ssem+"(COURSENO) ON DELETE CASCADE) ");
				 }
				 st.close();
				 con.close();
				 return;
			 }
			 else if(RecordType==SYLLABUS)
			 {
				 try
				 {
					 st.executeQuery("SELECT COURSENO FROM SYLLABUS_"+sbatch+0+ssem);
					 esi.setFlag();
					 //es.setFlag();
				 }
				 catch(SQLException e)
				 {
					 st.executeUpdate("CREATE TABLE SYLLABUS_"+sbatch+0+ssem+
					 "(COURSENO VARCHAR(10) PRIMARY KEY,SUBJECT VARCHAR(12),L NUMBER(1),T NUMBER(1),P NUMBER(1),"
					 +"THEORY NUMBER(3),SSNL NUMBER(3),PRACTICAL NUMBER(3),TOTAL NUMBER(3))");

				 }
				 st.close();
				 con.close();
				 return;
			 }

			 else if(RecordType==SYLLABUSPRESENT)
			 {
				 try
				 {
					 st.executeQuery("SELECT COURSENO FROM SYLLABUS_"+sbatch+0+ssem);
					 RecordType = LECTURERCHART;
					 TableChecker(RecordType);
					 LectChart lc=new LectChart(sbatch,ssem);
					 lc.setSize(700,500);
					 lc.setTitle("Lecture Chart");
					 lc.setVisible(true);
				 }
				 catch(SQLException e)
				 {
					 JOptionPane.showMessageDialog(this,"ENTER SYLLABUS FIRST");
				 }
				 st.close();
				 con.close();
				 return;
			  }
    		  else if(RecordType==LECTURERINFO)
    		  {
				  try
				  {
					  st.executeQuery("SELECT INITIALS FROM LECTURERINFO");

					  eli.setFlag();
				  }
				  catch(SQLException e)
				  {
					  st.executeUpdate("CREATE TABLE LECTURERINFO"+
					  "(NAME VARCHAR(30) ,INITIALS VARCHAR(3) ,"+
					  "PASSWORD VARCHAR(10),PRIMARY KEY(INITIALS))");
				  }
				  st.close();
				  con.close();
				  return;
			  }
			   else if(RecordType==SSNL)
			   {
				   course=pc.getCourse();
				   try
				   {
					   st.executeQuery("SELECT ROLLNO FROM SSNL_"+course+"_"+sbatch+0+ssem);
				   }
				   catch(SQLException e)
				   {
					   st.executeUpdate("CREATE TABLE SSNL_"+course+"_"+sbatch+0+ssem+
					   "(ROLLNO NUMBER(10) PRIMARY KEY,S1ATT NUMBER(2),S2ATT NUMBER(2),S3ATT NUMBER(2),S1 NUMBER(2),S2 NUMBER(2),S3 NUMBER(2)"+
					   ",A1 NUMBER(2),A2 NUMBER(2),A3 NUMBER(2),A4 NUMBER(2),A5 NUMBER(2),TTL NUMBER(3),BENEFIT NUMBER(2),TTLSSNL NUMBER(3))");
					   esni.setFlag();
				   }
				   st.close();
				   con.close();
				   return;
				}
				else if(RecordType==MAXATTENDANCE)
				{
				  try
				  {
					  st.executeQuery("SELECT COURSENO FROM MAXATTENDANCE_"+sbatch+0+ssem);
				  }
				  catch(SQLException e)
				  {
					  st.executeUpdate("CREATE TABLE MAXATTENDANCE_"+sbatch+0+ssem+
					  "(COURSENO VARCHAR(10) PRIMARY KEY,S1ATT NUMBER(2),S2ATT NUMBER(2),S3ATT NUMBER(2))");
				  }
				  st.close();
				  con.close();
				  return;
			  }
		 }
 		catch(ClassNotFoundException e)
 		{
			System.out.print (e);
		}
 		catch(SQLException e)
 		{
			System.out.print(e);
		}
	}
}

 class PChecker extends JFrame implements ActionListener
 {
 	JPanel p1,p2;
 	JLabel lpass;
 	TextField tpass;
 	static String passwd,str;
 	JButton ok,back;

 	//constructor
 	PChecker()
 	{
 		p1=new JPanel();
 		lpass=new JLabel("Password");
 		tpass=new TextField();
 		tpass.setEchoChar('*');
 		p1.setLayout(new GridLayout(1,2));
 		p1.add(lpass);
 		p1.add(tpass);
 		add(p1,BorderLayout.NORTH);
 		p2=new JPanel();
 		p2.setLayout(new GridLayout(1,2));
 		ok=new JButton("Ok");
 		back=new JButton("Back");
 		p2.add(ok);
 		p2.add(back);
 		add(p2,BorderLayout.SOUTH);
 		back.addActionListener(this);
 		ok.addActionListener(this);
 	}//end of constructor

 	public void actionPerformed(ActionEvent ae)
 	{
 		JButton b=(JButton)ae.getSource();
 		passwd=tpass.getText().toUpperCase();
 		if(b==ok)
 		{
 			//check for the missing entry
 			if((passwd.contentEquals("CODEPT")))
 			{
 				EnterStudInfo esi=new EnterStudInfo();
 				esi.setTitle("Enter Records");
 				esi.setSize(600,600);
 				esi.setVisible(true);
				this.setVisible(false);
 			}
 			else
 			{
 				JOptionPane.showMessageDialog(this,"INCORRECT PASSWORD");
			}
 		}//ok
 		if(b==back)
 		this.setVisible(false);
 	}//action performed
}//PChecker class


class FrontEnd extends JFrame implements ActionListener
{
  JPanel p1,p2;
 JButton lect,stud,exit;
 JLabel title;
 Font f=new Font("COMIC SANS MS",Font.BOLD,20);
 static String stri="hi";
 ImageIcon img;
 JLabel image;
Container con;
  //constructor
  FrontEnd()
  {
	con=getContentPane();
	img=new ImageIcon("logo.jpg");
	image=new JLabel(img);
	image.setBounds(80,120,400,400);
	con.add(image);
	p1=new JPanel();
	title=new JLabel("Automated Department Information Management System");
	title.setFont(f);
	p1.add(title);
	add(p1,BorderLayout.NORTH);
	p2=new JPanel();
	lect=new JButton("LECTURER");
	stud=new JButton("STUDENTS");
	exit=new JButton("EXIT");
	p2.add(lect);
	p2.add(stud);
	p2.add(exit);
	add(p2,BorderLayout.SOUTH);
	lect.addActionListener(this);
	exit.addActionListener(this);
	stud.addActionListener(this);
  }//end of constructor

  public void actionPerformed(ActionEvent ae)
  {
	JButton b=(JButton)ae.getSource();
	 if(b==lect)
	  {
		PChecker p=new PChecker();
		p.setTitle("AUTHENTICATION");
		p.setSize(300,200);
		p.setVisible(true);
	  }//lect
	  if(b==stud)
	  {
	    ViewInfo vi=new ViewInfo();
	    vi.setTitle("VIEW RECORDS");
	    vi.setSize(600,600);
	    vi.setVisible(true);
      }//stud
	 if(b==exit)
	  System.exit(1);
  }//actionPerformed
}//class FrontEnd


class Student
{
   public static void main(String args[])
    {
	  FrontEnd fe=new FrontEnd();
	  fe.setTitle("ADIMS");
	  fe.setSize(600,600);
	  fe.setVisible(true);
	 }//main
}//class Student

