# Proyecto final DAGSS 2019
Proyecto Java EE 7 


# PREVIO

* Instalación de NetBeans JEE (versión 8.2) con Servidor de Aplicaciones GlassFish (versión 4.1.1)
  * _Descarga_: [https://netbeans.org/downloads/8.2/](https://netbeans.org/downloads/8.2/)
  * _Pendiente_: Instalación con Netbeans 11 y Payara 5

* Descargar driver JDBC de MySQL y copiarlo en el directorio de librerias de GlassFish
   ```
   cd /tmp

   wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.18.zip

   unzip mysql-connector-java-8.0.18.zip

   cp mysql-connector-java-8.0.18/mysql-connector-java-8.0.18.jar \
      $HOME/glassfish-4.1.1/glassfish/domains/domain1/lib/

   pushd
   ```

* Crear BD `facturaaas` en MySQL
   ```
   $ mysql -u root -p    [pedirá la contraseña de MySQL]

   mysql> create user facturaaas@localhost identified by "facturaaas";
   mysql> create database facturaaas;
   mysql> grant all privileges on facturaaas.* to facturaaas@localhost;
   ```
   
   Adicionalmente, puede ser necesario establecer un formato de fecha compatible
   ```
   mysql> set @@global.time_zone = '+00:00';
   mysql> set @@session.time_zone = '+00:00';
   ```

* Descargar copia del proyecto desde GitHub
   ```
   git clone https://github.com/dagss2019/facturaaas.git
   ```

