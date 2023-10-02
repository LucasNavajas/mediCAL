
        function authorizeAndSendEmail(mailDestino, codigo) {
            gapi.load('client:auth2', () => {
                gapi.client.init({
                    apiKey: 'AIzaSyDFali7KngrOgzHUv9yNDxWN1QKO9OssxA',
                    clientId: '822180554034-ca8us3srrd4upoe5olukb21soljb8vvb.apps.googleusercontent.com',
                    discoveryDocs: ['https://www.googleapis.com/discovery/v1/apis/gmail/v1/rest'],
                    scope: 'https://www.googleapis.com/auth/gmail.send'
                }).then(() => {
                    // Realizar la autorización del usuario
                    return gapi.auth2.getAuthInstance().signIn();
                }).then(() => {
                    // Enviar el correo electrónico
                    sendEmail(mailDestino, codigo);
                }).catch(error => {
                    console.error(error);
                });
            });
        }

        function sendEmail(mailDestino, codigo) {
            const email = {
                to: mailDestino,
                subject: 'Código de Verificación',
                message: 'Tu código de verificación es:'+ codigo
            };

            const base64EncodedEmail = btoa(
                `To: ${email.to}\r\n` +
                `Subject: ${email.subject}\r\n\r\n` +
                `${email.message}`
            );

            gapi.client.gmail.users.messages.send({
                userId: 'me',
                resource: {
                    raw: base64EncodedEmail
                }
            }).then(response => {
                console.log('Correo enviado:', response);
            }).catch(error => {
                console.error('Error al enviar el correo:', error);
            });
        }
