package com.k8s.java.client.demo;

import java.io.IOException;
import io.kubernetes.client.openapi.models.V1Job;
import io.kubernetes.client.openapi.models.V1JobBuilder;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.BatchV1Api;

public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ApiClient client = ClientBuilder.cluster().build();
        Configuration.setDefaultApiClient(client);

        String namespace = "default";

        BatchV1Api api = new BatchV1Api(client);
        V1Job body = new V1JobBuilder()
          .withNewMetadata()
            .withNamespace(namespace)
            .withName("sample-job")
            .endMetadata()
          .withNewSpec()
            .withNewTemplate()
              .withNewMetadata()
                .addToLabels("name", "sample-job")
                .endMetadata()
              .editOrNewSpec()
                .addNewContainer()
                  .withName("main")
                  .withImage("alpine")
                  .addNewCommand("/bin/sh")
                  .addNewArg("-c")
                  .addNewArg("echo Starting Job")
                  .addNewArg("sleep 10")
                  .addNewArg("echo Ending Job")
                  .endContainer()
                .withRestartPolicy("Never")
                .endSpec()
              .endTemplate()
            .endSpec()
          .build();

          try {
            V1Job createdJob = api.createNamespacedJob(namespace, body, null, null, null);
            System.out.println(createdJob.toString());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        
        
    }

}
