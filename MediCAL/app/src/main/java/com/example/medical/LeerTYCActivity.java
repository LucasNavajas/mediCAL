package com.example.medical;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LeerTYCActivity extends AppCompatActivity {

    private TextView textViewTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminos_condiciones);

        textViewTerms = findViewById(R.id.text_terms);

        Button buttonFlagArg = findViewById(R.id.buttonFlagArg);
        buttonFlagArg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTextoEspaniol();
            }
        });

        Button buttonFlagUsa = findViewById(R.id.buttonFlagUsa);
        buttonFlagUsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTextoIngles();
            }
        });
    }

    private void mostrarTextoEspaniol() {
        String textoEspaniol =
                "SERVICIO MEDICAL PARA USUARIOS - Términos y Condiciones (\"Términos y Condiciones de uso\") actualizados Mayo, 2023\n\n" +
                "POR FAVOR, LEA ATENTAMENTE ESTOS TÉRMINOS Y CONDICIONES DE USO ANTES DE UTILIZAR NUESTRO SERVICIO DE DISPOSITIVO MÓVIL Y SITIO WEB RELACIONADO.\n" +
                "MediCAL le proporciona el Servicio de dispositivo móvil de MediCAL (la \"Aplicación\") y el sitio web relacionado (incluidas las versiones optimizadas para dispositivos móviles de dicho sitio web, el \"Sitio\") en virtud de estos Términos y condiciones (este \"Acuerdo\"). La Aplicación y el Sitio se denominan juntos como el \"Servicio\". Tal como se usa en estos Términos y condiciones, 'usted' o 'usuarios' se refiere a las personas que usan el Servicio, y \"Usuario Supervisor\" se refiere a las personas que pueden usar el Servicio para supervisar o ayudar a otros usuarios. Al usar el Servicio y/o al hacer clic en el botón \"Acepto\", usted acepta incondicionalmente seguir y estar sujeto a este Acuerdo y nuestra Política de Privacidad. Si no acepta estar sujeto y cumplir con todos los términos de este Acuerdo, no puede usar nuestro Servicio.\n\n" +
                "Usuarios Previstos\n" +
                "El Servicio está disponible solo para usuarios mayores de 18 años. Bajo ninguna circunstancia el Servicio debe ser utilizado por niños menores de 16 años, y no recopilaremos a sabiendas información personal de ninguna persona que sepamos que se encuentra en este grupo de edad. Si está utilizando el Servicio en beneficio de un niño, no proporcione información relacionada con dicho niño a menos que haya obtenido el consentimiento de los padres o tutores del niño, incluido su consentimiento a nuestra Política de Privacidad. Si descubre que su hijo ha estado usando el Servicio sin su consentimiento, o que alguien ha estado usando el Servicio para su hijo o en su nombre sin su consentimiento, comuníquese con nosotros utilizando la información a continuación en \"Cómo contactarnos\" y tomaremos medidas razonables para eliminar la información del niño de nuestras bases de datos activas. No puede proporcionar acceso ni usar el Servicio o el Contenido (tal como se define a continuación) en beneficio de terceros ni hacer un uso comercial del Servicio o del Contenido relacionado, pero puede usar el Servicio para su uso personal sujeto a este Acuerdo. El uso y acceso al Servicio es nulo donde esté prohibido. Al acceder y utilizar el Servicio, se asegurará de que (a) toda la información de registro que envíe sea veraz y precisa; (b) mantendrá la precisión de dicha información; (c) su uso del Servicio cumplirá y no violará ninguna ley, regulación, orden o directriz aplicable y (d) usted acepta recibir mensajes y material promocional que ofrece compras en el Servicio.\n\n" +
                "Modificaciones De Este Acuerdo\n"+
                "Nos reservamos el derecho de actualizar o modificar este Acuerdo en cualquier momento. Al continuar utilizando el Servicio después de dichos cambios, usted acepta seguir y estar sujeto a este Acuerdo tal como se modificó. Por estos motivos, lo alentamos a que revise periódicamente este Acuerdo.\n\n" +
                "Descargos De Responsabilidad\n" +
                "Su uso de cualquier aspecto del Servicio es bajo su propio riesgo. Debe consultar con los proveedores de atención médica y tomar sus decisiones médicas según sus consejos. Si usa y/o accede al Servicio en o desde un dispositivo Android que usted u otra persona rootearon o en o desde un dispositivo iOS que usted u otra persona liberaron, MediCAL no será responsable de la seguridad de sus datos, incluidos sus información personal, y usted asumirá toda la responsabilidad por cualquier incumplimiento, acceso ilegal, pérdida y/o corrupción de dichos datos. MediCAL NO PROPORCIONA NINGÚN CONSEJO MÉDICO DE NINGÚN TIPO. NO HACEMOS REPRESENTACIONES NI GARANTÍAS DE NINGÚN TIPO CON RESPECTO AL SERVICIO. LA INFORMACIÓN SOBRE MEDICAMENTOS, SALUD, CONSEJOS MÉDICOS Y OTROS PUEDE SER PROPORCIONADA POR TERCEROS, INCLUYENDO OTROS USUARIOS DEL SERVICIO. NO PODEMOS ACEPTAR NINGUNA RESPONSABILIDAD CON RESPECTO A CUALQUIER CONTENIDO PROPORCIONADO POR TERCEROS Y/O CUALQUIER OTRO USUARIO DEL SERVICIO. CUALQUIER ACCIÓN QUE TOME BASADA EN EL CONTENIDO, LAS NOTIFICACIONES O DE OTRO MODO PROPORCIONADO POR EL SERVICIO SE REALIZA BAJO SU PROPIO RIESGO. A. SIEMPRE DEBE VERIFICAR CUALQUIER INFORMACIÓN PROPORCIONADA A TRAVÉS DEL SERVICIO PARA ASEGURAR SU EXACTITUD. Investigaremos las presuntas violaciones de este Acuerdo o el comportamiento ilegal e inapropiado a través del Servicio. Tenga en cuenta que cooperaremos plenamente con cualquier investigación policial u orden judicial que nos ordene o nos indique que divulguemos la identidad, el comportamiento o las actividades de cualquier persona que se crea que ha violado este Acuerdo o se ha involucrado en un comportamiento ilegal. Puede solicitar la cancelación de su cuenta en cualquier momento y por cualquier motivo y puede ejercer sus derechos de acuerdo a la Ley argentina de Protección de Datos Personales (Ley No. 25.326), enviando un correo electrónico a info@MediCAL.com. Puede encontrar más información sobre sus derechos relacionados a la dicha Ley en nuestra Política de Privacidad . Cualquier suspensión o cancelación de su cuenta no afectará sus obligaciones con nosotros en virtud de este Acuerdo (incluidas, entre otras, la propiedad y la limitación de responsabilidad), incluidas todas aquellas obligaciones que, por su sentido y contexto, tienen la intención de sobrevivir a la suspensión o terminación de su cuenta."+
                "Suscripción a MediCAL Premium y Otros Servicios.\n" +
                "Podemos, a nuestra discreción, ofrecer varios servicios de suscripción, que incluyen, entre otros, la suscripción MediCAL Premium y usted puede optar por suscribirse a la misma. Su suscripción se renovará automáticamente a menos que desactive la renovación automática al menos 24 horas antes del final del período actual. Se le cobrará a su cuenta la renovación dentro de las 24 horas anteriores al final del período actual en ese momento. Puede administrar su suscripción y la renovación automática puede desactivarse yendo a la Configuración de su cuenta después de la compra. Cualquier parte no utilizada de un período de prueba gratuito, si se ofrece, se perderá cuando compre una suscripción a esa publicación, cuando corresponda.\n\n" +
                "Tecnología; Apoyo\n" +
                "No garantizamos que el Servicio funcione con su dispositivo móvil o informático o que sea compatible con el hardware o el software de cualquier dispositivo en particular. La información se transmitirá por un medio que estará fuera de nuestro control y jurisdicción; Múltiples factores, incluida la disponibilidad de la red, pueden afectar la entrega de alertas o notificaciones o interferir con el funcionamiento del Servicio. Sin limitar lo anterior, nosotros, nuestros otorgantes de licencia y nuestros proveedores no hacemos representaciones ni garantías sobre (1) la disponibilidad, precisión, confiabilidad, integridad, calidad, rendimiento, idoneidad o puntualidad del Servicio, Contenido, incluido software, texto, gráficos , enlaces o comunicaciones proporcionadas en o a través del uso del Servicio; o (2) el cumplimiento de cualquier regulación gubernamental que requiera la divulgación de información sobre productos de medicamentos recetados o la aprobación o el cumplimiento de cualquier herramienta de software con respecto al Servicio. No tenemos ninguna obligación de proporcionar soporte técnico o mantenimiento para el Servicio. Aunque tomamos medidas razonables para mantener el Servicio libre de virus, gusanos, troyanos u otros códigos que contengan propiedades destructivas, no garantizamos que los archivos disponibles para descargar a través del Servicio estén libres de dichas contaminaciones." +
                "Responsabilidades Del Usuario\n" +
                "Si nos envía información a través del Servicio o relacionada con este, o si nos envía información comercial, comentarios, ideas, conceptos o invenciones por correo electrónico, debe asegurarse de que dicha información no sea confidencial y de que tiene todos los permisos necesarios para enviar o poner a disposición dicha información. Además, acepta que:\n" +
                "● no reproducirá, duplicará, copiará, venderá, revenderá ni explotará el Servicio, su Contenido, su software o cualquier parte de cualquiera de los anteriores;\n" +
                "● no utilizará el Servicio para ningún propósito que infrinja las leyes locales, estatales, nacionales o internacionales;\n" +
                "● no solicitará la contraseña o información personal de otra persona bajo pretextos falsos;\n" +
                "● no se hará pasar por otra persona o entidad ni tergiversará su afiliación con una persona o entidad, y/o usará o accederá a la cuenta o contraseña de otro usuario sin permiso;\n" +
                "● no violará los derechos legales de otros, incluidos difamar, abusar, acechar o amenazar a los usuarios;\n" +
                "● no infringirá los derechos de propiedad intelectual, los derechos de privacidad o los derechos morales de ningún tercero;\n" +
                "● no publicará ni transmitirá ningún Contenido que sea (o crea razonablemente o deba creer razonablemente que sea) ilegal, fraudulento o no autorizado, o que promueva dicha actividad, o que involucre (o crea razonablemente o deba creer razonablemente que involucre) cualquier material robado, ilegal, falsificado, fraudulento, pirateado o no autorizado;\n" +
                "● no publicará falsedades o tergiversaciones, incluso con respecto a cualquier información médica o de salud; y\n" +
                "● no publicará ni transmitirá ningún Contenido que sea (o razonablemente se deba entender que es) calumnioso, difamatorio, obsceno, ofensivo (incluido el material que promueva o glorifique el odio, la violencia o la intolerancia o que sea inapropiado para la ética de la comunidad del Servicio).\n" +
                "Usted acepta no interferir ni intentar interferir con el correcto funcionamiento del Servicio o interrumpir las operaciones o violar la seguridad del Servicio. Las violaciones del funcionamiento o la seguridad del sistema o de la red pueden dar lugar a responsabilidades civiles o penales. Investigaremos posibles ocurrencias de tales violaciones, y podemos involucrar y cooperar con las autoridades encargadas de hacer cumplir la ley en el enjuiciamiento de cualquier persona involucrada en tales violaciones. Usted acepta cumplir con todas las responsabilidades y obligaciones del usuario según lo establecido en este Acuerdo. La falta de cumplimiento o nuestra falta de acción con respecto a un incumplimiento por parte de usted u otros de este Acuerdo no constituye consentimiento o renuncia, y nos reservamos el derecho de hacer cumplir dicho término a nuestra entera discreción. Ninguna renuncia a cualquier incumplimiento o incumplimiento en virtud del presente se considerará una renuncia a cualquier incumplimiento o incumplimiento anterior o posterior. Nada de lo contenido en este Acuerdo se interpretará como una limitación de las acciones o recursos disponibles para nosotros con respecto a cualquier actividad o conducta prohibida." +
                "Concesión De Licencia\n" +
                "Por la presente le otorgamos una licencia limitada, no exclusiva, no asignable y no sublicenciable para acceder y usar nuestro Servicio, y cualquier guía de usuario, especificaciones o documentación relacionada (la \"Documentación\"), sujeto a los términos y condiciones de este acuerdo. Esta licencia es solo para su uso personal y no comercial y solo por el término de este Acuerdo. En la medida en que no esté limitado o restringido por ninguna ley o regulación aplicable, se le otorga permiso para descargar temporalmente una copia de la Aplicación para uso personal, no comercial, solo en cada dispositivo móvil que posea o controle. No puede distribuir ni hacer que la aplicación esté disponible para que otros la usen en varios dispositivos simultáneamente. Bajo esta licencia, excepto y solo en la medida en que cualquiera de las siguientes restricciones esté prohibida por la ley aplicable o cualquiera de las actividades restringidas esté permitida por los términos de licencia de cualquier componente de código abierto incorporado en la Aplicación, usted no puede:\n" +
                "● prestar, alquilar, arrendar, vender, redistribuir, ceder, sublicenciar o transferir de otro modo la Aplicación o el derecho a descargar o usar la Aplicación;\n" +
                "● usar el Servicio para cualquier propósito comercial o para cualquier exhibición pública comercial o no comercial;\n" +
                "● copiar, descompilar, realizar ingeniería inversa, desensamblar, intentar derivar el código fuente de la aplicación, cualquier actualización de la aplicación o cualquier parte de la Aplicación o actualizaciones, o intentar hacer cualquiera de los anteriores;\n" +
                "● copiar, modificar o crear trabajos derivados del Servicio, la Documentación de cualquier Servicio o las actualizaciones de la Documentación o cualquier parte del Servicio, Documentación o actualizaciones;\n" +
                "● eliminar cualquier aviso de derechos de autor u otros avisos de propiedad de la Aplicación, la Documentación, parte de la Aplicación o del Sitio;\n" +
                "● transferir el Contenido o los materiales de la Aplicación o el Sitio a cualquier otra persona o \"duplicar\" lo mismo en cualquier servidor;\n" +
                "● eludir, deshabilitar o interferir de otro modo con las funciones relacionadas con la seguridad del Servicio o las funciones que impiden o restringen uso o copia de cualquier contenido;\n" +
                "● usar cualquier robot, araña, servicio de búsqueda o recuperación de sitios, o cualquier otro dispositivo o proceso manual o automático para recuperar, indexar, extraer datos, o reproducir o eludir de cualquier forma la estructura de navegación o la presentación del Servicio;\n" +
                "● recolectar, recopilar o extraer información sobre otros usuarios del Servicio;\n" +
                "● publicar o transmitir cualquier virus, gusano troyano u otro elemento dañino o perjudicial; o\n" +
                "● violar cualquier ley, regla o regulación aplicable.\n" +
                "Si viola cualquiera de estas restricciones, esta licencia terminará automáticamente y puede estar sujeto a enjuiciamiento y daños."+
                "Propiedad\n" +
                "MediCAL y sus licenciantes son propietarios del Sitio, la Documentación y la Aplicación, incluido cualquier material o Contenido disponible a través del Servicio, incluido nuestro algoritmo patentado, y todos los derechos de propiedad intelectual en todo el mundo sobre lo anterior. Salvo que se permita expresamente en este documento, no puede copiar, desarrollar, reproducir, volver a publicar, modificar, alterar, descargar, publicar, transmitir, transmitir o utilizar de otro modo cualquier material disponible en el Servicio. No eliminará, alterará ni ocultará ningún derecho de autor, marca registrada, marca de servicio u otros avisos de derechos de propiedad incorporados en el Servicio. Todas las marcas comerciales son marcas comerciales o marcas comerciales registradas de sus respectivos propietarios. Nada en este Acuerdo le otorga ningún derecho a usar ninguna marca comercial, marca de servicio, logotipo o nombre comercial nuestro o de un tercero.\n" +
                "Infracción\n" +
                "No aceptamos ninguna responsabilidad por cualquier material proporcionado o publicado por un usuario, a su entera discreción. Haremos un esfuerzo razonable para monitorear y moderar el contenido publicado por los usuarios en busca de cualquier contenido ilegal obvio. Si cree que algo que aparece en el Servicio infringe sus derechos de autor, puede enviarnos un aviso solicitando que se elimine o que se bloquee el acceso. Le sugerimos que consulte a su asesor legal antes de presentar una notificación o contranotificación. Tenga en cuenta que puede haber sanciones importantes por reclamos falsos. Es nuestra política cancelar las cuentas de los infractores reincidentes en las circunstancias apropiadas.\n" +
                "Terminación\n" +
                "Este Acuerdo es efectivo hasta que usted o nosotros lo rescindamos. Puede rescindir este Acuerdo en cualquier momento, siempre que interrumpa cualquier uso posterior del Servicio. Si viola este Acuerdo, nuestro permiso para usar el Servicio termina automáticamente. Podemos, a nuestro exclusivo criterio, rescindir este Acuerdo y su acceso a una parte o la totalidad del Servicio, en cualquier momento y por cualquier motivo, después de notificarle, sin penalización ni responsabilidad hacia usted o cualquier tercero. En el caso de que usted incumpla este Acuerdo, estas acciones se suman y no reemplazan o limitan cualquier otro derecho o recurso que pueda estar disponible para nosotros. En caso de que usted o nosotros rescindamos el Acuerdo, debe desinstalar de inmediato la Aplicación en todos sus dispositivos y destruir todos los materiales descargados u obtenidos de otro modo del Servicio, toda la Documentación y todas las copias de dichos materiales y Documentación. Las siguientes disposiciones sobreviven a la expiración o terminación de este Acuerdo por cualquier motivo: Exenciones de responsabilidad, Propiedad, Limitaciones de responsabilidad, Elección de ley y foro, Acuerdo completo y Divisibilidad." +
                "Elección de la ley y foro\n" +
                "Este Acuerdo se regirá en todos los aspectos por las leyes de la República Argentina, sin incluir sus disposiciones sobre elección de ley o conflicto de leyes. En cualquier reclamo o acción que surja directa o indirectamente de este Acuerdo o en relación con el Servicio, usted acepta irrevocablemente someterse a dicha jurisdicción.\n" +
                "Acuerdo Completo\n" +
                "Este Acuerdo constituye el acuerdo completo entre usted en relación con el objeto del mismo. Todo lo contenido o entregado a través del Servicio que sea inconsistente o entre en conflicto con los términos de este Acuerdo queda reemplazado por los términos de este Acuerdo. Este Acuerdo no puede modificarse, en su totalidad o en parte, excepto como se describe en otra parte de este Acuerdo. Este acuerdo puede ser reemplazado por cualquier término acordado entre un individuo y MediCAL.\n" +
                "Divisibilidad: Si alguna de las disposiciones de este Acuerdo se determina que no es exigible por un tribunal u otro tribunal de jurisdicción competente, dichas disposiciones se modificarán, limitarán o eliminarán en la medida mínima necesaria para que este Acuerdo permanezca en pleno vigor. y efecto\n" +
                "Asignabilidad: Usted acepta que este Acuerdo y todos los acuerdos incorporados entre usted y nosotros pueden ser asignados por nosotros, a nuestro exclusivo criterio, a cualquier tercero.\n" +
                "Información De Contacto\n" +
                "Todos los avisos que se le envíen en relación con este Acuerdo se publicarán en el Servicio o se le enviarán al correo electrónico o a la dirección física, si corresponde, que nos haya proporcionado. La notificación se considerará entregada cuando se publique en el Servicio o cuando se envíe el correo electrónico, a menos que se notifique a la parte que envía que la dirección de correo electrónico no es válida.\n" +
                "Última actualización: Mayo, 2023.\n";
        textViewTerms.setText(textoEspaniol);
    }

    private void mostrarTextoIngles() {
        String textoIngles =

                "MediCAL USERS SERVICE – Terms and Conditions (“Terms and Conditions of service”) updated May, 2023\n" +
                "PLEASE READ THESE TERMS AND CONDITIONS OF USE CAREFULLY BEFORE USING OUR MOBILE DEVICE SERVICE AND RELATED WEBSITE.\n" +
                "MediCAL, provides you with the MediCAL mobile device Service (the “App”) and related website (including the mobile-optimized versions of such website, the “Site”) under these Terms and Conditions (this “Agreement”). The App and the Site are referred to together as the “Service.” As used in these Terms and Conditions, ‘you’ or ‘users’ refers to individuals using the Service, and “Medfriends” refers to individuals who may use the Service to supervise or support other users. By using the Service and/or by clicking the “I Agree” button, you unconditionally agree to follow and be bound by this Agreement and our Privacy Policy. If you do not agree to be bound by and comply with all of the terms of this Agreement, you may not use our Service.\n" +
                "Intended Users\n" +
                "The Service is available only to users who are at least 18 years old. Under no circumstances should the Service be used by children under 16 years of age, and we will not knowingly collect personal information from any person we know to be in this age group. If you are using the Service for the benefit of a child, please do not provide information relating to such child unless you have obtained the child’s parents’ or guardians’ consent, including their consent to our Privacy Policy. If you discover that your child has been using the Service without your consent, or that someone has been using the Service for or on behalf of your child without your consent, please contact us using the information below under “How to Contact Us,” and we will take reasonable steps to delete the child’s information from our active databases. You may not provide access to or use the Service or Content (as defined below) thereof for the benefit of third parties or make commercial use of the Service or related Content, but you may use the Service for your personal use subject to this Agreement. Use of and access to the Service is void where prohibited. By accessing and using the Service, you shall ensure that (a) any and all registration information you submit is truthful and accurate; (b) you will maintain the accuracy of such information; (c) your use of the Service will comply with and does not violate any applicable law, regulation, order or guideline and (d) you consent to receiving messages and promotional material offering in-Service purchases." +
                "Modifications of this Agreement\n" +
                "We reserve the right to update or modify this Agreement at any time. By continuing to use the Service after any such changes, you agree to follow and be bound by this Agreement as changed. For these reasons, we encourage you to periodically review this Agreement.\n" +
                "Disclaimers\n" +
                "Your use of any aspect of the Service is at your own risk. You must consult with healthcare providers and make your medical decisions based on their advice. If you use and/or access the Service on or from an Android device which you or someone else rooted or on or from an iOS device which you or someone else jail broke, MediCAL shall not be responsible for the security of your data, including your personal information, and you shall bear all responsibility for any breach, illegal access, loss and/or corruption of such data. MediCAL IS NOT PROVIDING ANY MEDICAL ADVICE OF ANY KIND. WE MAKE NO REPRESENTATIONS OR WARRANTIES WHATSOEVER IN RESPECT OF THE SERVICE. INFORMATION REGARDING MEDICATIONS, HEALTH, MEDICAL ADVICE AND OTHERWISE MAY BE PROVIDED BY THIRD PARTIES, INCLUDING OTHER USERS OF THE SERVICE. WE CANNOT ACCEPT ANY LIABILITY WHATSOEVER IN RESPECT OF ANY SUCH CONTENT WHICH IS PROVIDED BY THIRD PARTIES AND/OR ANY OTHER USERS OF THE SERVICE. ANY ACTIONS YOU TAKE BASED ON CONTENT, NOTIFICATIONS AND OTHERWISE PROVIDED BY THE SERVICE ARE TAKEN AT YOUR SOLE RISK. A. YOU SHOULD ALWAYS CHECK ANY INFORMATION PROVIDED THROUGH THE SERVICE TO ENSURE ITS ACCURACY We will investigate suspected violations of this Agreement or illegal and inappropriate behavior through the Service. Please note that we will fully cooperate with any law enforcement investigation or court order ordering us or directing us to disclose the identity, behavior or activities of anyone believed to have violated this Agreement or to have engaged in illegal behavior. You may request termination of your account at any time and for any reason and you may exercise your rights according to the Argentinian Law on Protection of Personal Data (Law No. 25,326), by sending an e-mail to info@MediCAL.com. More information about your rights pertaining to this law can be found in our Privacy Policy. Any suspension or termination of your account shall not affect your obligations to us under this Agreement (including but not limited to ownership and limitation of liability), including all those obligations, which by their sense and context are intended to survive the suspension or termination of your account.\n" +
                "Subscription for MediCAL Premium and Other Services.\n" +
                "We may at our discretion offer various subscription services, including, without limitation, the MediCAL Premium subscription and you may choose to subscribe for the same. Your subscription will automatically renew unless you turn off auto-renew at least 24 hours before the end of the then current period. Your account will be charged for renewal within 24-hours prior to the end of the then current period. You may manage your subscription and the auto-renewal may be turned off by going to your Account Settings after purchase. Any unused portion of a free trial period, if offered, will be forfeited when you purchase a subscription to that publication, where applicable.\n" +
                "Technology; Support\n" +
                "We do not warrant or guarantee that the Service will function with your mobile or computing device or be compatible with the hardware or software on any particular devices. Information will be transmitted over a medium that will be beyond our control and jurisdiction; multiple factors, including network availability, may affect alert or notification delivery or otherwise interfere with the operation of the Service. Without limiting the foregoing, we, our licensors, and our suppliers make no representations or warranties about (1) the availability, accuracy, reliability, completeness, quality, performance, suitability or timeliness of the Service, Content, including software, text, graphics, links, or communications provided on or through the use of the Service; or (2) the satisfaction of any government regulations requiring disclosure of information on prescription drug products or the approval or compliance of any software tools with regard to the Service. We have no obligation to provide technical support or maintenance for the Service. Although we take reasonable measures to keep the Service free of viruses, worms, Trojan horses or other code that contain destructive properties, we do not warrant or guarantee that files available for downloading through the Service will be free of such contaminations.\n" +
                "User’s Responsibilities\n" +
                "If you submit any information to us through or related to the Service or send us any business information, feedback, idea, concept or invention to us by e-mail, you shall ensure that such information is not confidential and that you have all necessary permission to submit or otherwise make available such information. You further agree that:\n" +
                "● you will not reproduce, duplicate, copy, sell, resell, or exploit the Service, its Content, its software or any portion of any of the foregoing;\n" +
                "● you will not use the Service for any purpose in violation of local, state, national or international laws;\n" +
                "● you will not solicit another person’s password or personal information under false pretenses;\n" +
                "● you will not impersonate another person or entity or otherwise misrepresent your affiliation with a person or entity, and/or use or access another user’s account or password without permission;\n" +
                "● you will not violate the legal rights of others, including defaming, abuse, stalking or threatening users;\n" +
                "● you will not infringe the intellectual property rights, privacy rights, or moral rights of any third party;\n" +
                "● you will not post or transmit any Content that is (or you reasonably believe or should reasonably believe to be) illegal, fraudulent, or unauthorized, or furthers such activity, or that involves (or you reasonably believe or should reasonably believe to involve) any stolen, illegal, counterfeit, fraudulent, pirated, or unauthorized material;\n" +
                "● you will not publish falsehoods or misrepresentations, including with respect to any medical or health information; and\n" +
                "● you will not post or transmit any Content that is (or reasonably should be understood to be) libelous, defamatory, obscene, offensive (including material promoting or glorifying hate, violence, or bigotry or otherwise inappropriate to the community ethos of the Service).\n" +
                "You agree not to interfere or attempt to interfere with the proper working of the Service or to disrupt the operations or violate the security of the Service. Violations of system or network operation or security may result in civil or criminal liability. We will investigate possible occurrences of such violations, and we may involve and cooperate with law enforcement authorities in prosecuting anyone involved with such violations. You agree to comply with all user responsibilities and obligations as stated in this Agreement. Non-enforcement or our failure to act with respect to a breach by you or others of this Agreement does not constitute consent or waiver, and we reserve the right to enforce such term at our sole discretion. No waiver of any breach or default hereunder shall be deemed to be a waiver of any preceding or subsequent breach or default. Nothing contained in this Agreement shall be construed to limit the actions or remedies available to us with respect to any prohibited activity or conduct."+
                "We hereby grant to you a limited, non-exclusive, non-assignable, non-sublicensable license to access and use our Service, and any user guides, specifications or related documentation (the “Documentation”), subject to the terms and conditions of this Agreement. This license is only for your personal and non-commercial use and only for the term of this Agreement. To the extent not limited or restricted under any applicable law or regulation, you are granted permission to temporarily download one copy of the App for personal, non-commercial use only on each mobile device that you own or control. You may not distribute or make the App available for use by others on multiple devices simultaneously. Under this license, except as and only to the extent any of the following restrictions are prohibited by applicable law or any of the restricted activities are permitted by the licensing terms of any open-sourced components incorporated into the App, you may not:\n" +
                "● lend, rent, lease, sell, redistribute, assign, sublicense or otherwise transfer the App or the right to download or use the App;\n" +
                "● use the Service for any commercial purpose or for any commercial or non-commercial public display;\n" +
                "● copy, decompile, reverse engineer, disassemble, attempt to derive the source code of the App, any App updates, or any part of the App or updates, or attempt to do any of the foregoing;\n" +
                "● copy, modify or create derivative works of the Service, Documentation any Service or Documentation updates or any part of the Service, Documentation or updates;\n" +
                "● remove any copyright or other proprietary notices from the App, Documentation, part of the App or from the Site;\n" +
                "● transfer the Content or materials from the App or Site to anyone else or “mirror” the same on any server;\n" +
                "● circumvent, disable, or otherwise interfere with security-related features of the Service or features that prevent or restrict use or copying of any content;\n" +
                "● use any robot, spider, site search or retrieval Service, or any other manual or automatic device or process to retrieve, index, data-mine, or in any way reproduce or circumvent the navigational structure or presentation of the Service;\n" +
                "● harvest, collect or mine information about other users of the Service;\n" +
                "● post or transmit any virus, worm Trojan horse or other harmful or disruptive element; or\n" +
                "● violate any applicable law, rule or regulation.\n" +
                "If you violate any of these restrictions, this license will automatically terminate, and you may be subject to prosecution and damages.\n" +
                "Ownership\n" +
                "MediCAL and its licensors own the Site, Documentation and App, including any material or Content made available through the Service, including our proprietary algorithm, and all worldwide intellectual property rights in the foregoing. Except as expressly permitted herein, you may not copy, further develop, reproduce, re-publish, modify, alter download, post, broadcast, transmit or otherwise use any material made available in the Service. You will not remove, alter or conceal any copyright, trademark, service mark or other proprietary rights notices incorporated in the Service. All trademarks are trademarks or registered trademarks of their respective owners. Nothing in this Agreement grants you any right to use any trademark, service mark, logo, or trade name of ours or any third party."+
                "We accept no responsibility or liability for any material provided or posted by a user, at his solely discretion. We will make a reasonable effort to monitor and moderate the content posted by users for any obvious illegal content. If you believe that something appearing on the Service infringes your copyright, you may send us a notice requesting that it be removed, or access to it blocked. We suggest that you consult your legal advisor before filing a notice or counter-notice. Be aware that there can be substantial penalties for false claims. It is our policy to terminate the accounts of repeat infringers in appropriate circumstances.\n" +
                "Termination\n" +
                "This Agreement is effective until terminated by either you or us. You may terminate this Agreement at any time, provided that you discontinue any further use of the Service. If you violate this Agreement, our permission to you to use the Service automatically terminates. We may, in our sole discretion, terminate this Agreement and your access to any or all of the Service, at any time and for any reason, after notifying you, without penalty or liability to you or any third party. In the event of your breach of this Agreement, these actions are in addition to and not in lieu or limitation of any other right or remedy that may be available to us. Upon any termination of the Agreement by either you or us, you must promptly uninstall the App on all of your devices and destroy all materials downloaded or otherwise obtained from the Service, all Documentation, and all copies of such materials and Documentation. The following provisions survive the expiration or termination of this Agreement for any reason whatsoever: Disclaimers, Ownership, Limitations on Liability, Choice of Law and Forum, Entire Agreement and Severability.\n" +
                "Choice of Law and Forum\n" +
                "This Agreement shall be governed in all respects by the laws of the Argentine Republic, without including its provisions on choice of law or conflict of laws. In any claim or action arising directly or indirectly from this Agreement or in connection with the Service, you irrevocably agree to submit to such jurisdiction.\n" +
                "Entire Agreement\n" +
                "This Agreement constitutes the entire agreement between you pertaining to the subject matter hereof. Anything contained in or delivered through the Service that is inconsistent with or conflicts with the terms of this Agreement is superseded by the terms of this Agreement. This Agreement may not be modified, in whole or in part, except as described elsewhere in this Agreement. This agreement may be superseded by any terms agreed between an individual and MediCAL.\n" +
                "Severability If any of the provisions of this Agreement are held to be not enforceable by a court or other tribunal of competent jurisdiction, then such provisions shall be amended, limited or eliminated to the minimum extent necessary so that this Agreement shall otherwise remain in full force and effect.\n" +
                "Assignability You agree that this Agreement and all incorporated agreements between you and us may be assigned by us, in our sole discretion to any third party.\n" +
                "Contact Information\n" +
                "All notices to you relating to this Agreement shall be posted on the Service or sent to you at the e-mail or physical address, if any, that you provided to us. Notice shall be deemed given when notice is posted on the Service or when the e-mail is sent, unless the sending party is notified that the e-mail address is invalid.\n" +
                "Last updated: May, 2023.\n";

        textViewTerms.setText(textoIngles);
    }
}