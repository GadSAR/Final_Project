package com.example.jmsrabbitmq.publisher;

import com.example.jmsrabbitmq.dto.ContactUsForm;
import com.example.jmsrabbitmq.dto.MailForm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

@Service
public class RabbitMQProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    @Value("${rabbitmq.contact-us-to-management.exchange.name}")
    private String contactUsToManagement_exchangeName;
    @Value("${rabbitmq.contact-us-to-management.routing.key}")
    private String contactUsToManagement_routingKey;
    @Value("${rabbitmq.contact-us-to-client.exchange.name}")
    private String contactUsToClient_exchangeName;
    @Value("${rabbitmq.contact-us-to-client.routing.key}")
    private String contactUsToClient_routingKey;
    @Value("${rabbitmq.user-to-admin.exchange.name}")
    private String userToAdmin_exchangeName;
    @Value("${rabbitmq.user-to-admin.routing.key}")
    private String userToAdmin_routingKey;
    @Value("${rabbitmq.admin-to-user.exchange.name}")
    private String adminToUser_exchangeName;
    @Value("${rabbitmq.admin-to-user.routing.key}")
    private String adminToUser_routingKey;
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailToManagement(ContactUsForm contactUsForm) throws MessagingException {
        String from = contactUsForm.getEmail();
        String to = "ifmcompany123@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("New IFM's Contact Us Form");
        helper.setFrom(from);
        helper.setTo(to);

        String content = "<div style='font-family: Arial, sans-serif; font-size: 16px; line-height: 1.5;'>"
                + "<p style='font-weight: bold; margin-bottom: 10px;'>The Request is from " + contactUsForm.getEmail() + "</p>"
                + "<p style='margin-bottom: 10px;'>Subject: " + contactUsForm.getSubject() + "</p>"
                + "<p style='margin-bottom: 20px;'>" + contactUsForm.getMessage() + "</p>"
                + "<p style='font-weight: bold; margin-bottom: 10px;'>Best Regards, " + contactUsForm.getName() + "</p>"
                + "<img src='https://d1oco4z2z1fhwp.cloudfront.net/templates/default/1826/Image_1.png' style='max-width: 100%; margin-top: 20px;'>"
                + "</div>";
        helper.setText(content, true);

        mailSender.send(message);
    }

    public void sendEmailToClient(ContactUsForm contactUsForm) throws MessagingException {
        String from = "ifmcompany123@gmail.com";
        String to = contactUsForm.getEmail();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("IFM's thank u for your request");
        helper.setFrom(from);
        helper.setTo(to);

        helper.setText("""
                <!DOCTYPE html>
                       <html xmlns:v='urn:schemas-microsoft-com:vml' xmlns:o='urn:schemas-microsoft-com:office:office' lang='en'>
                       
                       <head>
                       	<title></title>
                       	<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
                       	<meta name='viewport' content='width=device-width, initial-scale=1.0'><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]-->
                       	<style>
                       		* {
                       			box-sizing: border-box;
                       		}
                       
                       		body {
                       			margin: 0;
                       			padding: 0;
                       		}
                       
                       		a[x-apple-data-detectors] {
                       			color: inherit !important;
                       			text-decoration: inherit !important;
                       		}
                       
                       		#MessageViewBody a {
                       			color: inherit;
                       			text-decoration: none;
                       		}
                       
                       		p {
                       			line-height: inherit
                       		}
                       
                       		.desktop_hide,
                       		.desktop_hide table {
                       			mso-hide: all;
                       			display: none;
                       			max-height: 0px;
                       			overflow: hidden;
                       		}
                       
                       		.image_block img+div {
                       			display: none;
                       		}
                       
                       		@media (max-width:620px) {
                       			.desktop_hide table.icons-inner {
                       				display: inline-block !important;
                       			}
                       
                       			.icons-inner {
                       				text-align: center;
                       			}
                       
                       			.icons-inner td {
                       				margin: 0 auto;
                       			}
                       
                       			.fullMobileWidth,
                       			.row-content {
                       				width: 100% !important;
                       			}
                       
                       			.mobile_hide {
                       				display: none;
                       			}
                       
                       			.stack .column {
                       				width: 100%;
                       				display: block;
                       			}
                       
                       			.mobile_hide {
                       				min-height: 0;
                       				max-height: 0;
                       				max-width: 0;
                       				overflow: hidden;
                       				font-size: 0px;
                       			}
                       
                       			.desktop_hide,
                       			.desktop_hide table {
                       				display: table !important;
                       				max-height: none !important;
                       			}
                       		}
                       	</style>
                       </head>
                       
                       <body style='background-color: #FFFFFF; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;'>
                       <table class='nl-container' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF;'>
                       	<tbody>
                       	<tr>
                       		<td>
                       			<table class='row row-1' align='center' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #016ba6; background-size: auto;'>
                       				<tbody>
                       				<tr>
                       					<td>
                       						<table class='row-content stack' align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-size: auto; border-radius: 0; color: #000000; width: 600px;' width='600'>
                       							<tbody>
                       							<tr>
                       								<td class='column column-1' width='100%' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;'>
                       									<table class='image_block block-1' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt;'>
                       										<tr>
                       											<td class='pad' style='padding-bottom:30px;padding-left:25px;padding-right:25px;padding-top:30px;width:100%;'>
                       												<div class='alignment' align='center' style='line-height:10px'><a href='http://localhost:5173' target='_blank' style='outline:none' tabindex='-1'><img src='https://github.com/GadSAR/Final_Project/blob/main/IFM_IntelliFleetManagement/react_frontend_ifm/public/ifm_icon.png?raw=true' style='display: block; height: auto; border: 0; width: 120px; max-width: 100%;' width='120'></a></div>
                       											</td>
                       										</tr>
                       									</table>
                       									<table class='image_block block-2' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt;'>
                       										<tr>
                       											<td class='pad' style='width:100%;padding-right:0px;padding-left:0px;'>
                       												<div class='alignment' align='center' style='line-height:10px'><img class='fullMobileWidth' src='https://d1oco4z2z1fhwp.cloudfront.net/templates/default/4011/top-rounded.png' style='display: block; height: auto; border: 0; width: 600px; max-width: 100%;' width='600'></div>
                       											</td>
                       										</tr>
                       									</table>
                       								</td>
                       							</tr>
                       							</tbody>
                       						</table>
                       					</td>
                       				</tr>
                       				</tbody>
                       			</table>
                       			<table class='row row-2' align='center' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #245f89; background-size: auto;'>
                       				<tbody>
                       				<tr>
                       					<td>
                       						<table class='row-content stack' align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-size: auto; background-color: #ffffff; color: #000000; width: 600px;' width='600'>
                       							<tbody>
                       							<tr>
                       								<td class='column column-1' width='100%' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;'>
                       									<table class='heading_block block-1' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt;'>
                       										<tr>
                       											<td class='pad' style='padding-bottom:5px;padding-top:25px;text-align:center;width:100%;'>
                       												<h1 style='margin: 0; color: #555555; direction: ltr; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 36px; font-weight: normal; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0;'><strong>Thanks you for your request!</strong></h1>
                       											</td>
                       										</tr>
                       									</table>
                       									<table class='text_block block-2' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;'>
                       										<tr>
                       											<td class='pad' style='padding-bottom:20px;padding-left:15px;padding-right:15px;padding-top:20px;'>
                       												<div style='font-family: sans-serif'>
                       													<div class style='font-size: 14px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 25.2px; color: #737487; line-height: 1.8;'>
                       														<p style='margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 32.4px;'><span style><span style='font-size:18px;'>We </span></span><span style='font-size:18px;'>received</span><span style><span style='font-size:18px;'> your request about:
                       														""" + contactUsForm.getSubject() + "," + """
                                     and we will contact you soon :)</span></span></p>
                       														<p style='margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 25.2px;'><span style><span style='font-size:18px;'>Best regards, </span></span><span style><span style='font-size:18px;'>IFM company.</span></span></p>
                       													</div>
                       												</div>
                       											</td>
                       										</tr>
                       									</table>
                       									<table class='button_block block-3' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt;'>
                       										<tr>
                       											<td class='pad' style='padding-bottom:20px;padding-left:15px;padding-right:15px;padding-top:20px;text-align:center;'>
                       												<div class='alignment' align='center'><!--[if mso]><v:roundrect xmlns:v='urn:schemas-microsoft-com:vml' xmlns:w='urn:schemas-microsoft-com:office:word' href='http://localhost:5173/login' style='height:52px;width:158px;v-text-anchor:middle;' arcsize='8%' stroke='false' fillcolor='#3b65e5'><w:anchorlock/><v:textbox inset='0px,0px,0px,0px'><center style='color:#ffffff; font-family:Arial, sans-serif; font-size:16px'><![endif]--><a href='http://localhost:5173/login' target='_blank' style='text-decoration:none;display:inline-block;color:#ffffff;background-color:#3b65e5;border-radius:4px;width:auto;border-top:0px solid transparent;font-weight:undefined;border-right:0px solid transparent;border-bottom:0px solid transparent;border-left:0px solid transparent;padding-top:10px;padding-bottom:10px;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;font-size:16px;text-align:center;mso-border-alt:none;word-break:keep-all;'><span style='padding-left:55px;padding-right:55px;font-size:16px;display:inline-block;letter-spacing:normal;'><span dir='ltr' style='word-break: break-word; line-height: 32px;'>Log in</span></span></a><!--[if mso]></center></v:textbox></v:roundrect><![endif]--></div>
                       											</td>
                       										</tr>
                       									</table>
                       								</td>
                       							</tr>
                       							</tbody>
                       						</table>
                       					</td>
                       				</tr>
                       				</tbody>
                       			</table>
                       			<table class='row row-3' align='center' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #47a9ec;'>
                       				<tbody>
                       				<tr>
                       					<td>
                       						<table class='row-content stack' align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-position: center top; color: #000000; width: 600px;' width='600'>
                       							<tbody>
                       							<tr>
                       								<td class='column column-1' width='100%' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;'>
                       									<table class='image_block block-1' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt;'>
                       										<tr>
                       											<td class='pad' style='width:100%;padding-right:0px;padding-left:0px;'>
                       												<div class='alignment' align='center' style='line-height:10px'><img class='fullMobileWidth' src='https://d1oco4z2z1fhwp.cloudfront.net/templates/default/4011/bottom-rounded.png' style='display: block; height: auto; border: 0; width: 600px; max-width: 100%;' width='600'></div>
                       											</td>
                       										</tr>
                       									</table>
                       									<table class='text_block block-2' width='100%' border='0' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;'>
                       										<tr>
                       											<td class='pad' style='padding-bottom:30px;padding-left:5px;padding-right:5px;padding-top:35px;'>
                       												<div style='font-family: sans-serif'>
                       													<div class style='font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #262b30; line-height: 1.2;'>
                       														<p style='margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 16.8px;'><span style='font-size:12px;'>© 2023 IntelliFleetManagement |&nbsp; Israel</span></p>
                       													</div>
                       												</div>
                       											</td>
                       										</tr>
                       									</table>
                       								</td>
                       							</tr>
                       							</tbody>
                       						</table>
                       					</td>
                       				</tr>
                       				</tbody>
                       			</table>
                       		</td>
                       	</tr>
                       	</tbody>
                       </table><!-- End -->
                       </body>
                       
                       </html>
                       """, true);

        mailSender.send(message);
    }

    public void sendEmail(MailForm mailForm) throws MessagingException {
        String from = mailForm.getSender();
        String to = mailForm.getRecipient();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("New IFM's Mail Form");
        helper.setFrom(from);
        helper.setTo(to);

        String content = "<div style='font-family: Arial, sans-serif; font-size: 16px; line-height: 1.5;'>"
                + "<p style='font-weight: bold; margin-bottom: 10px;'>The Request is from " + mailForm.getSender() + "</p>"
                + "<p style='margin-bottom: 10px;'>Subject: " + mailForm.getSubject() + "</p>"
                + "<p style='margin-bottom: 20px;'>" + mailForm.getMessage() + "</p>"
                + "<p style='font-weight: bold; margin-bottom: 10px;'>Best Regards!" + "</p>"
                + "<img src='https://d1oco4z2z1fhwp.cloudfront.net/templates/default/1826/Image_1.png' style='max-width: 100%; margin-top: 20px;'>"
                + "</div>";
        helper.setText(content, true);

        mailSender.send(message);
    }

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send_contactUsToManagement(List<String> form) {
        LOGGER.info("Sending contactUsToManagement message to RabbitMQ: " + form.get(1));
        rabbitTemplate.convertAndSend(contactUsToManagement_exchangeName, contactUsToManagement_routingKey, form);
    }

    public void send_contactUsToClient(List<String> form) {
        LOGGER.info("Sending contactUsToClient message to RabbitMQ: " + form.get(1));
        rabbitTemplate.convertAndSend(contactUsToClient_exchangeName, contactUsToClient_routingKey, form);
    }

    public void send_userToAdmin(List<String> form) {
        LOGGER.info("Sending userToAdmin message to RabbitMQ: " + form.get(2));
        rabbitTemplate.convertAndSend(userToAdmin_exchangeName, userToAdmin_routingKey, form);
    }

    public void send_adminToUser(List<String> form) {
        LOGGER.info("Sending adminToUser message to RabbitMQ: " + form.get(2));
        rabbitTemplate.convertAndSend(adminToUser_exchangeName, adminToUser_routingKey, form);
    }
}
