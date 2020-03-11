# Bulk mail from command line

I have a spreadsheet with a mailing list and a template that corresponds to the body of the mail that I am going to send. This kind of applications are called mail merger or something like that.

This application works as follows:

1.- Open mailing list csv file, i.e.

contact,email,...
Jhone Doe,jhon@doe.com,..
Lorem ipsum,lorem@ipsum.com,...
...

2.- Load the template of the email, i.e:

Dear ${contact},

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac quam tortor. Aenean scelerisque eros varius, dapibus tellus ut, auctor ligula. Suspendisse tempor ut erat at tempor. Mauris et blandit ...


3.- Use first row as header, the only required column is email. The rest of the columns are fields, say you have a column “salary”, then in the template all ocurrences of ${salary} will be replaced by the values in this column in each row.

4.- For each row take the template an replace the occurrences of the fields with the corresponding values.

5.- send the email.


Usage:

git clone ...
cd ...
mvn install

java -jar ./target/spring-boot-send-email-1.0.jar --template=/tmp/mail-template.txt --mail-list=/tmp/correos-prueba.csv --subject="Testing command line"

Cloned from:
Article link : https://www.mkyong.com/spring-boot/spring-boot-how-to-send-email-via-smtp/