//File name: JCalcFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class JCalcFrame extends JFrame implements ActionListener
{
  FlowLayout flow = new FlowLayout();
  JLabel title = new JLabel("Mortgage Calculator");

  //creates button to enter farenheit value
  JLabel principal = new JLabel("Mortgage principal");
  JLabel rate = new JLabel("Interest rate (%)");
  JLabel term = new JLabel("Mortgage term (years)");

  //user input text field
  JTextField userP = new JTextField("100000", 3);

  double Rate[] = {5.35, 5.50, 5.75};
  int Term[] = {7, 15, 30};

  String Dropdown[] = {"7 years at 5.35%", "15 years at 5.50%", "30 years at 5.75%"};
  JComboBox dropdown = new JComboBox(Dropdown);

  JButton calcButton = new JButton("Calculate");
  
  JButton exitButton = new JButton("Exit");

  JButton reset = new JButton("Reset");

  JLabel result = new JLabel("Your monthly payment is: ");
  JLabel sum = new JLabel("  ");

  JTextArea textArea = new JTextArea(15, 40);
  JScrollPane displayScroll = new JScrollPane(textArea,
    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

  //creates the general look of the UI
  public JCalcFrame()
  {
    Container con = getContentPane();
    Box outer = Box.createVerticalBox();
    // Title
    Box row0 = Box.createHorizontalBox();
    // Entry Fields
    Box row1 = Box.createHorizontalBox();
    Box col1 = Box.createVerticalBox();
    Box col2 = Box.createVerticalBox();
    Box col3 = Box.createVerticalBox();

    // Total
    Box row2 = Box.createHorizontalBox();
    Box col4 = Box.createVerticalBox();
    Box col5 = Box.createVerticalBox();
    Box col6 = Box.createVerticalBox();
    Box row3 = Box.createHorizontalBox();
    Box row4 = Box.createHorizontalBox();

    //sets container layout to flow
    con.setLayout(flow);

    //creates heading title 22pt font
    title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
    title.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));

    //creates boxes to organize the layout
    con.add(outer);
    outer.add(row0);
    //adds title
    row0.add(title);

    //adds row1 to outside container
    outer.add(row1);

    //adds 3 columns to row1
    row1.add(col1);
    row1.add(col2);
    row1.add(col3);

    //margin spacing
    col1.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
    col2.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
    
    //adds input title and text field to col1
    principal.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
    principal.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
    col1.add(principal);
    userP.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    userP.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
    col1.add(userP);

    //adds input title and text field to col2
    rate.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
    rate.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
    col2.add(rate);
  
    //creates a styled JComboBox
    dropdown.setEditable(true);
    JTextField boxField = (JTextField)dropdown.getEditor().getEditorComponent();
    boxField.setBorder(BorderFactory.createEmptyBorder());
    dropdown.setBackground(new Color(0,0,0,0));
    dropdown.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
    dropdown.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15)); 
    col2.add(dropdown);

    //adds spacing and other components
    row2.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
    outer.add(row2);
    row2.add(col4);
    row2.add(col5);
    row2.add(col6);
    
    //adds spacing and calcButton
    sum.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));

    //displays total monthly Mortgage
    col6.add(result);
    sum.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
    col6.add(sum);

    row3.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
    outer.add(row3);
    row3.add(calcButton);
    row3.add(reset);  
    row3.add(exitButton);

    outer.add(row4);
    row4.add(displayScroll);

    calcButton.addActionListener(this);
    exitButton.addActionListener(this);
    reset.addActionListener(this);
  }
  public void actionPerformed(ActionEvent e)
  {
     Object source = e.getSource();
     if(source == calcButton)
     {
        //sets the decimal to round to 2 decimals
        String pattern = "###,###.##";
        DecimalFormat df = new DecimalFormat(pattern);

        int index = dropdown.getSelectedIndex();
        String num1 = userP.getText();

        double n1 = Double.parseDouble(num1);
        double n2 = Rate[index];
        int n3 = Term[index];

        //calculates monthly payment due
        //n1 = principal, n2 = rate, n3 = term
        n2 = n2/1200;
        n3 = n3*12;
        double monthlyPayment = (n1 * n2) / (1 - Math.pow(1+n2,-n3));
 
        //converts total to string
        String output = "$" + df.format(monthlyPayment);

        if((n1 < 0) || (n2 < 0) || (n3 < 0)) {
          output = "Invalid values. Try again";
        }
        sum.setText(output);
        double balance = n1 + (n1*n2);
        StringBuffer sb = new StringBuffer();
        String header = String.format("%10s %30s %30s\n", "Month", "Montly Payment ($)", "Remaining Balance ($)");
        sb.append(header);
        sb.append("\n");

        for (int i = 1; i < n3; i++) {
          balance -= monthlyPayment;
          if ((balance - monthlyPayment) < 0) {
            balance = 0.00;
          }
          String text = String.format("%10d %30.2f %40.2f\n", i, monthlyPayment, balance);
          sb.append(text);
        }
        textArea.setText(sb.toString());
      }
      else if(source == reset)
      {
        sum.setText("");
        userP.setText("");
      }
      else
      {
        // if the user clicks on the Exit button (source is Exit button)
        System.exit(0);
      }
   }// end actionPerformed
}// end JCalcFrame

