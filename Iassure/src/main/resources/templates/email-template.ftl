<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>User Creation</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 12px;
        }

        .login-button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #20c5a0;
            border-radius: 8px;
            color: #fff;
            font-family: 'Open Sans', Helvetica, Arial, sans-serif;
            text-decoration: none;
            font-size: 16px; 
            padding: 15px 30px; 
        }

        .login-button:hover {
            background-color: #007b6f;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">
    <table border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
        <tr>
            <td>
                <p>Dear ${firstname},</p>
                <p>Thank you for signing up!</p>
                <p>Your temporary password is: ${password}</p>
                <p>Please use this password to log in to your account.</p>
				<p> Username :${email}</p>

				<p> Please Click the below link to Login</p>
				<a href="http://3.27.226.110:3000/" class="incident-link">Visit Site</a>

                <p>If you have any questions or need further assistance, feel free to contact us.</p>
                <p>Best regards,</p>
                <p>INNOCLIQUE TECHNOLOGIES</p>

            </td>
        </tr>
        <tr>
        </tr>
    </table>
</body>
</html>
