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

Create and populate a mail template file, i.e. /tmp/mail-template.txt

Create and populate csv mailing list file, i.e. /tmp/maillist.csv

```
mvn install
java -jar ./target/bulk-mail-1.0.jar --template=/tmp/mail-template.txt --mail-list=/tmp/maillist.csv --subject="Testing command line"
```

### Mailing list csv format, i.e.
```
contact,email,...
Jhone Doe,jhon@doe.com,..
Lorem ipsum,lorem@ipsum.com,...
...
```

### Template email format, i.e:

```
Dear ${contact},

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac quam tortor. Aenean scelerisque eros varius, dapibus tellus ut, auctor ligula. Suspendisse tempor ut erat at tempor. Mauris et blandit ...

```

###  Details

Use first row as header, the only required column is email. The rest of the columns are fields, say you have a column “salary”, then in the template all ocurrences of ${salary} will be replaced by the values in this column in each row.

For each row take the template an replace the occurrences of the fields with the corresponding values.

send the email.


Cloned from:

Article link : https://www.mkyong.com/spring-boot/spring-boot-how-to-send-email-via-smtp/
