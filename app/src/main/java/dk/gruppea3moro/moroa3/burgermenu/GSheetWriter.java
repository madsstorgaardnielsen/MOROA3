
    /*package dk.gruppea3moro.moroa3.burgermenu;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GSheetWriter {
    private static Sheets sheetService;
    private static String APP_NAME = "MOROA3";
    private static String SHEET_ID = "1ypwWZz6o66cTsSBClh_6UBDfDjIKBn_CmC1JNw3jIoo";

    private Credential auth() throws IOException, GeneralSecurityException {
        InputStream in = GSheetWriter.class.getResourceAsStream("/credentials.json");
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + "credentials.json");
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");

        return credential;
    }

    public Sheets getSheetService() throws IOException, GeneralSecurityException {
        Credential credential = auth();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APP_NAME)
                .build();
    }

    public void run() throws IOException, GeneralSecurityException {
        sheetService = getSheetService();
        String range = "TipsOsSheet!:A1:C5";

        ValueRange response = sheetService.spreadsheets().values().get(SHEET_ID, range).execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No Data");
        } else {
            for (List<?> row : values) {
                System.out.println(row.get(1) + " " + row.get(2) + " " + row.get(3) + "\n");
            }
        }
    }
}

*/

