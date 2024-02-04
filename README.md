<b>ENGLISH-</b> MediCAL is a mobile application that serves as a medication calendar for individual users and is also applicable to healthcare institutions. 
Users are allowed to upload all their medical treatments, as well as keep track of their symptoms and vital signs.
Additionally, there is also a web application intended for system administrators to take care of the parameterization of the database tables and for administrators of the different healthcare institutions using the app to manage 
professional permissions, patient calendars, etc.
<br>
This system is divided into three main elements:
<br>

+SpringServer: It is a server developed in Java with Spring Framework and Gradle. It handles requests from both the mobile and web applications, performing queries and modifications to the System's database.
It also handles notifications to users' devices, informing them and their contacts when it's time to take a medication registered in the calendar. 
It periodically backs up the database, allowing manual backups and database recovery using these copies.
<br>

+MediCAL: Mobile application developed in Java with Android Studio. To use it, a user must create their own account with their email and password, along with certain required data. 
This account is stored in a Firebase application for later login. Once logged in, the user has 1 to 3 personal calendars, in which they can register their own medical treatments or the ones that the person they are responsible for has. 
Also, the user can keep track of their symptoms and vital signs presented throughout the days. There is a section of tips that helps users maintain a healthy lifestyle and teaches them how to use the app. 
Another important functionality is the contacts section, where users are allowed to control the calendars of other MediCAL accounts, with certain restrictions. 
Last but not least, users can view statistics and reports in Excel format on their consumed medications, symptoms, etc.

+MediCAL Web: Web application developed in HTML, CSS, and JavaScript. Focused on administrators of institutions using the App and also on the system and database administrator. 
Depending on the type of user who logs in, it allows managing the parameterization of different aspects of the databases and the application. 
If it is a healthcare institution administrator, it allows managing the health professionals of the institution who will use the application, the calendars they handle, and their permissions within the mobile application. 
In case it is the system administrator, it allows managing the parameters of the application in general (for example, available symptoms, existing medications, user permissions, etc.) and performing manual backups and recovery of the database.
<br>
<br>
<b>ESPAÑOL-</b>
MediCAL es una aplicación móvil que sirve como calendario de medicamentos para usuarios particulares y que a su vez es aplicable a instituciones de la salud. 
Se le permite a los usuarios cargar todos sus tratamientos médicos, así como también llevar registro de sus síntomas y signos vitales.
Además, también se cuenta con una aplicación web destinada al administrador del sistema para la parametrización de las tablas de la base de datos y para administradores de las diferentes instituciones de salud que utilizan la app,
para gestionar permisos de los profesionales, calendarios de pacientes, etcétera.
<br>
<br>
Este sistema se divide en tres elementos principales:
<br>
<br>
+SpringServer: Es un servidor desarrollado en Java con Spring Framework y Gradle. Se encarga de manejar las peticiones tanto de la aplicación móvil como la web, realizando consultas y modificaciones a la base de datos del Sistema.
También se encarga del manejo de las notificaciones a los dispositivos de los usuarios, haciéndole saber a los mismos y a sus contactos cuándo es la hora de tomar un medicamento registrado en el calendario.
Realiza copias de seguridad de la base de datos periódicamente, permitiendo a su vez realizar copias manuales y también recuperar la base de datos utilizando alguna de estas copias.
<br>
<br>
+MediCAL: Aplicación móvil desarrollada en Java con Android Studio. Para utilizarla, un usuario debe crear una cuenta propia con su correo electrónico y una contraseña, sumado a ciertos datos obligatorios. Esta cuenta se almacena en
una aplicación de FireBase para realizar su login posteriormente. Una vez iniciado sesión, el usuario posee de 1 a 3 calendarios propios, en los que puede registrar tratamientos médicos propios o de la persona que tenga a su cargo.
También, el usuario puede llevar registro de sus síntomas y signos vitales presentados a lo largo de los días. Se cuenta con una sección de consejos que ayuda a los usuarios a mantener un estilo de vida sano y les enseña a usar la app.
Otra funcionalidad importante es la de contactos, donde se les permite a los usuarios controlar los calendarios de otras 
cuentas de MediCAL, con ciertas restricciones. Como última funcionalidad, los usuarios pueden visualizar estadísticas y reportes en formato excel sobre sus medicamentos consumidos, síntomas, etc.
<br>
<br>
+MediCAL Web: Aplicación web desarrollada en HTML, CSS y JavaScript. Enfocada a los administradores de las instituciones que utilicen la App y también al administrador del sistema y base de datos. De acuerdo al tipo de usuario que inicia 
sesión, se le permite gestionar la parametrización de diferentes aspectos de las bases de datos y de la aplicación. Si se trata de un administrador de institución de salud, se le permite gestionar a los profesionales de salud de la misma
que utilizarán la aplicación, los calendarios que manejan y sus permisos dentro de la aplicación móvil. En caso de que se trate del administrador del sistema, se permite gestionar los parámetros de la aplicación en general (por ejemplo, 
síntomas disponibles, medicamentos existentes, permisos de usuarios, etc) y realizar copias de seguridad y recuperación de la BD manuales.
