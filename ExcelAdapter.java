import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.datatransfer.*;
import java.util.*;

 //ExcelAdapter enables Paste Clipboard functionality on JTables.

public class ExcelAdapter implements ActionListener
{
   private String rowstring,value;
   private Clipboard system;
   private JTable jTable1 ;

   public ExcelAdapter(JTable myJTable)
   {
      jTable1 = myJTable;
      KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK,false);
      // Identifying the Paste KeyStroke user can modify this

      jTable1.registerKeyboardAction(this,"Paste",paste,JComponent.WHEN_FOCUSED);
      system = Toolkit.getDefaultToolkit().getSystemClipboard();
   }

   public void actionPerformed(ActionEvent e)
   {

      if (e.getActionCommand().compareTo("Paste")==0)
      {
          System.out.println("Trying to Paste");
          int startRow=(jTable1.getSelectedRows())[0];
          int startCol=(jTable1.getSelectedColumns())[0];
          try
          {
             String trstring= (String)(system.getContents(this).getTransferData(DataFlavor.stringFlavor));
             System.out.println("String is:"+trstring);
             StringTokenizer st1=new StringTokenizer(trstring,"\n");
             for(int i=0;st1.hasMoreTokens();i++)
             {
                rowstring=st1.nextToken();
                StringTokenizer st2=new StringTokenizer(rowstring,"\t");
                for(int j=0;st2.hasMoreTokens();j++)
                {
                   value=(String)st2.nextToken();
                   if (startRow+i< jTable1.getRowCount()  && startCol+j< jTable1.getColumnCount())
                   jTable1.setValueAt(value,startRow+i,startCol+j);
                   System.out.println("Putting "+ value+"atrow="+startRow+i+"column="+startCol+j);
               }
            }
         }
         catch(Exception ex)
         {
			 ex.printStackTrace();
		 }
      }
   }
}