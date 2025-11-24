My project name is : " cgpaCalculator "
It is a JavaFX Application.
This app is build for calculating CGPA based on university grading rules.
Three pages are there in this project.From home page one user can navigate to gpa input page and result page.Three FXML files are used for the design in scene builder.These are:
home-view.fxml
gpa-view.fxml
result-view.fxml
First these three fxml files are created.Then these files are pushed to github repo.Then contollers are build in main java file.These are : 
HomeController.java
CgpaController.java
ResultController.java
The launcher is renamed as CgpaApplication.CgpaApplication is used to run this cgpaCalculator application.In this file main function is stayed to launch the app.
CgpaController is used for adding courses, removing courses, tracking total credits,navigates result-view.fxml. Also passes course list to ResultController.
ResultController.java is used for Showing final GPA ,displaying table of all added courses
Course.java is used storing all data files like string,int,double etc.
CgpaCalculation Formula:
                          Total Points = Σ(credit × gradePoint)
                           GPA = Total Points / Total Credits
style.css to style background, buttons etc.There is also a toggle button to change the theme of the application.



