To start the database daemon
- pg_ctl -D /usr/local/var/postgres start

To start the REST server
- cd server
- sudo nodemon
  or
  npm start

To start the web server
- export HTTPS=true
- npm start

To run the web app
- browse https://localhost
- click "ADVANCED"
- click "Proceed to localhost (unsafe)"
- browse https://localhost:3000

To run database demo
- cd server
- node demo.js

This project was bootstrapped with
[Create React App](https://github.com/facebookincubator/create-react-app).
