# Send bulk mail from command line

Given a csv file with a mailing list and a template with the body of the mail, you will send your mailing list.

Usage:

```
git clone https://github.com/hanspoo/bulk-mail-cli
cd bulk-mail-cli
```

In src/main/resources/application.properties modify the email account and password used for send email.

An alternative is to override with environment variables:

```
export SPRING_MAIL_USERNAME=my-user@gmail.com
export SPRING_MAIL_PASSWORD=123456
```

```
mvn install
java -jar ./target/bulk-mail-1.0.jar --template=/tmp/mail-template.txt --mail-list=/tmp/correos-prueba.csv --subject="Testing command line"
```

The code does this:

1.- Open mailing list csv file, i.e.
```
contact,email,...
Jhone Doe,jhon@doe.com,..
Lorem ipsum,lorem@ipsum.com,...
...
```

2.- Load the template of the email, i.e:

```
Dear ${contact},

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac quam tortor. Aenean scelerisque eros varius, dapibus tellus ut, auctor ligula. Suspendisse tempor ut erat at tempor. Mauris et blandit ...

```

3.- Use first row as header, the only required column is email. The rest of the columns are fields, say you have a column “salary”, then in the template all ocurrences of ${salary} will be replaced by the values in this column in each row.

4.- For each row take the template an replace the occurrences of the fields with the corresponding values.

5.- send the email.


Cloned from:
Article link : https://www.mkyong.com/spring-boot/spring-boot-how-to-send-email-via-smtp/
