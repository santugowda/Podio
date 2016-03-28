Podio Android App : The task is to implement a small app that authenticates a user against the Podio API using email and password and displays a list of the user’s workspaces, grouped by organization. We are free to use any libraries, tools or structure you want to implement this app, but you are not allowed to use Podio’s client libraries or SDKs (i.e. the libraries available at developers.podio.com/clients).

I have implemented this by using Google Volley Library. Important aspect of login process is to get access token assosiated with the user and use it to get a JSON response which contains the a JSONArray elemets. Using Volley have parsed the JSONArray and got the Organization and the Workspace for which the user is registered in form of listview.

To login, need to create an account in Podio else below is the default username, password and few important links in order to get the url which contains a JSON response.

Username : sebrehtest+puzzle@gmail.com Password: password1234

Developer url : https://developers.podio.com/authentication Details for Login : https://developers.podio.com/authentication/username_password Details to Get organizations API call : https://developers.podio.com/doc/organizations/get-organizations-22344
