/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draft.dev.proveidentitydemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
//Prove Imports Begin
import com.prove.proveapi.Proveapi;
import com.prove.proveapi.models.components.Security;
import com.prove.proveapi.models.components.V3CompleteIndividualRequest;
import com.prove.proveapi.models.components.V3CompleteRequest;
import com.prove.proveapi.models.components.V3CompleteResponse;
import com.prove.proveapi.models.components.V3StartRequest;
import com.prove.proveapi.models.components.V3StartResponse;
import com.prove.proveapi.models.components.V3ValidateRequest;
import com.prove.proveapi.models.components.V3ValidateResponse;
import com.prove.proveapi.models.operations.V3CompleteRequestResponse;
import com.prove.proveapi.models.operations.V3StartRequestResponse;
import com.prove.proveapi.models.operations.V3ValidateRequestResponse;
//Prove Imports End

/**
 *
 * @author Mdu Sibisi
 */
@Service
public class IdentityService {

    @Value("${spring.application.client.id}")
    private String clientId;
    @Value("${spring.application.client.secret}")
    private String clientSecret;
    @Value("${spring.application.token.url}")
    private String tokenURL;

    private Proveapi sdk;

    private void initializeProveSDK() {
        sdk = Proveapi.builder()
                .security(Security.builder()
                        .tokenURL(tokenURL)
                        .clientID(clientId)
                        .clientSecret(clientSecret)
                        .build())
                .build();
    }

    public String verifyIdentity(Identity identity) {
        // Create client for Prove API.
        if (sdk == null) {
            initializeProveSDK();
        }

        // Send the start request.                              
        V3StartRequest req = V3StartRequest.builder()
                .phoneNumber(identity.getPhoneNumber())
                .flowType(identity.getFlowType())
                .finalTargetUrl("https://www.example.com")
                .build();

        try {
            V3StartRequestResponse res = sdk.v3().v3StartRequest()
                    .request(req)
                    .call();

            //REMOVE
            System.out.println(res);
            System.out.println("Start response received: " + res);

            V3StartResponse startResponse = res.v3StartResponse().get();
            //REMOVE
            System.out.println("authorization token: " + startResponse.authToken());

            return startResponse.authToken();

        } catch (Exception ex) {
            System.getLogger(IdentityService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return new String();
    }

    public boolean validate(String coId) {

        if (sdk == null) {
            initializeProveSDK();
        }

        V3ValidateRequest req = V3ValidateRequest.builder()
                .correlationId(coId)
                .build();

        try {
            V3ValidateRequestResponse res = sdk.v3().v3ValidateRequest()
                    .request(req)
                    .call();

            V3ValidateResponse validateResponse = res.v3ValidateResponse().get();

            return validateResponse.success();

        } catch (Exception ex) {
            System.getLogger(IdentityService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }

    public Boolean completeValidation(Identity identity, String correlationId) {

        if (sdk == null) {
            initializeProveSDK();
        }

        try {

            V3CompleteRequest req = V3CompleteRequest.builder().correlationId(correlationId)
                    .individual(V3CompleteIndividualRequest.builder()
                            .emailAddresses(java.util.List.of(
                                    identity.getEmailAddress()))
                            .firstName(identity.getFirstName())
                            .lastName(identity.getLastName())
                            .build())
                    .build();

            V3CompleteRequestResponse res = sdk.v3().v3CompleteRequest()
                    .request(req)
                    .call();

            V3CompleteResponse completeResponse = res.v3CompleteResponse().get();

            return completeResponse.success();
        } catch (Exception ex) {
            System.getLogger(IdentityService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }

}
