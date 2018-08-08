function connexion()
{
    var mysql      = require('mysql');
    var connection = mysql.createConnection(
    {
      host     : 'localhost',
      user     : 'root',
      password : ''
    }
    );

    connection.query('SELECT from bd_compta.langues', function(err, rows) {
      // connected! (unless `err` is set) 
      console.log("coucou");
      console.log(rows);
    });
}