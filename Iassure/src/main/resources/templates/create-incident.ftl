<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Incident Created</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 14px;
            line-height: 1.6;
        }

        .incident-link {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            border-radius: 5px;
            color: #ffffff;
            text-decoration: none;
            font-weight: bold;
            margin-top: 10px;
        }

        .incident-link:hover {
            background-color: #0056b3;
        }

        .incident-details {
            margin: 20px 0;
            padding: 15px;
            border: 1px solid #dcdcdc;
            border-radius: 5px;
            background-color: #f9f9f9;
        }

        .footer {
            font-size: 12px;
            color: #666;
            margin-top: 20px;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td>
                <p>An incident has been created with the following details:</p>

                <div class="incident-details">
                    <p><strong>Severity:</strong> ${severity}</p>
                    <p><strong>Title:</strong> ${title}</p>
                </div>

                <p>To view more details or take action, click the button below:</p>
                <a href="http://3.27.226.110:3000/?redirect=/incident/details/${incidentId}" class="incident-link">View Incident</a>

                <p class="footer">If you have any questions, please contact support at noreply-rmwilliams@innoclique.com.</p>

                <p>Best regards,<br />
                INNOCLIQUE TECHNOLOGIES</p>
            </td>
        </tr>
    </table>
</body>
</html>
