import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LMS extends JFrame implements ActionListener {
    private static final String[] COURSES = {
        "Computer Programming",
        "English for Academic and Professional Purposes",
        "EMTEC or Empowerment Technologies",
        "Practical Research",
        "Physical Education and Health",
        "Statistics and Probability",
        "Pagbasa at Pagsusuri ng Ibat Ibang Teksto Tungo sa Pananaliksik",
        "English for Reading and Writing"
    };    
	
	    private JTextField nameField;
	    private JComboBox<String> gradeLevelComboBox;
	    private JTextField strandField;
	    private JButton continueButton1;
	    private JPanel coursesPanel;
	    private JButton continueButton2;
	    private JPanel selectedCoursesPanel;
	    private JButton showAvailableCoursesButton;
	    
	    public LMS() {
	        setTitle("LMS");
	        setSize(500, 500);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	      // Create the first panel for the registration form.
	        JPanel formPanel = new JPanel(new GridLayout(0, 2));
	        formPanel.add(new JLabel("Name:"));
	        nameField = new JTextField();
	        nameField.setPreferredSize(new Dimension(150, 30));
	        formPanel.add(nameField);
	        formPanel.add(new JLabel("Grade level:"));
	        gradeLevelComboBox = new JComboBox<>(new String[] {"Grade 11", "Grade 12"});
	        formPanel.add(gradeLevelComboBox);
	        formPanel.add(new JLabel("Strand/Major:"));
	        strandField = new JTextField();
	        strandField.setPreferredSize(new Dimension(300, 30));
	        formPanel.add(strandField);
	        continueButton1 = new JButton("Continue");
	        continueButton1.addActionListener(this);
	        formPanel.add(continueButton1);
	
	        setContentPane(formPanel);
	        setVisible(true);
	    }
	
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == continueButton1) {
            //save the information from the registration form.
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name.");
                return;
            }
            
            String strand = strandField.getText().trim();
            if (strand.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your strand/major.");
                return;
            }

            // Create the course selection panel
            coursesPanel = new JPanel(new GridLayout(0, 1));
            for (String course : COURSES) {
                JCheckBox checkbox = new JCheckBox(course);
                coursesPanel.add(checkbox);
            }

            continueButton2 = new JButton("Continue");
            continueButton2.addActionListener(this);
            coursesPanel.add(continueButton2);
	
            // Switch to the course selection panel
            setContentPane(coursesPanel);
            revalidate();

        } else if (e.getSource() == continueButton2) {
            // Collect the selected courses
            selectedCoursesPanel = new JPanel(new GridLayout(0, 1));
            for (Component c : coursesPanel.getComponents()) {
                if (c instanceof JCheckBox) {
                    JCheckBox checkbox = (JCheckBox) c;
                    if (checkbox.isSelected()) {
                        JLabel label = new JLabel(checkbox.getText());
                        selectedCoursesPanel.add(label);
                    }
                }
            }

            // Show a button to view the available courses
            showAvailableCoursesButton = new JButton("Show available courses");
            showAvailableCoursesButton.addActionListener(this);
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            topPanel.add(showAvailableCoursesButton);
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(selectedCoursesPanel, BorderLayout.CENTER);
            setContentPane(mainPanel);
            revalidate();

        } else if (e.getSource() == showAvailableCoursesButton) {
            // Show a dialog with the available courses
            JDialog dialog = new JDialog(this, "Available Courses", true);
            JPanel panel = new JPanel(new GridLayout(0, 1));
            for (String course : COURSES) {
                boolean selected = false;
                for (Component c : selectedCoursesPanel.getComponents()) {
                    if (c instanceof JLabel && course.equals(((JLabel) c).getText())) {
                        selected = true;
                        break;
                    }
                }
                if (!selected) {
                    JCheckBox checkbox = new JCheckBox(course);
                    panel.add(checkbox);
                }
            }

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e2 -> dialog.dispose());

            JButton addCoursesButton = new JButton("Add Selected Courses");
            addCoursesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add the selected courses to the selected courses panel
                    for (Component c : panel.getComponents()) {
                        if (c instanceof JCheckBox && ((JCheckBox) c).isSelected()) {
                            JLabel label = new JLabel(((JCheckBox) c).getText());
                            selectedCoursesPanel.add(label);
                        }
                    }
					          selectedCoursesPanel.revalidate();
        			      selectedCoursesPanel.repaint();
                    dialog.dispose();
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(addCoursesButton);
            buttonPanel.add(closeButton);
            panel.add(buttonPanel);
            dialog.setContentPane(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new LMS();
    }
}