const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

exports.addUser = functions.auth.user().onCreate(async (userRecord, ctx) => {
    let user = await admin.auth().getUser(userRecord.uid);
    var displayName = user.displayName;
    if(displayName === null) 
    {
        displayName = "Quatsch";
    }
  admin.database().ref('/user').push({'userId': displayName});
});

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
