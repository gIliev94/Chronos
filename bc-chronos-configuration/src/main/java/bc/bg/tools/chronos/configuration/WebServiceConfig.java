package bc.bg.tools.chronos.configuration;

//@Configuration
public class WebServiceConfig {

    // private static final Logger LOGGER =
    // Logger.getLogger(WebServiceConfig.class);

    public static final String SERVICES_BASE_URL = "/soap-api";
    public static final String INTEGRATION_SERVICE_URL = "/SAPInterface";

    // /**
    // * @return
    // */
    // @Bean
    // public ServletRegistrationBean cxfServlet() {
    // return new ServletRegistrationBean(new CXFServlet(), SERVICES_BASE_URL +
    // "/*");
    // }
    //
    // /**
    // * DEFAULT_BUS_ID => Handles initialization of all CXF-related objects.
    // *
    // * @return
    // */
    // @Bean(name = Bus.DEFAULT_BUS_ID)
    // public SpringBus springBus() {
    // return new SpringBus();
    // }
    //
    // // TODO: Spring alternative to CXF - requires more complex configuration.
    // // @Bean
    // // public ServletRegistrationBean wsServlet() {
    // // return new ServletRegistrationBean(new WSSpringServlet(),
    // // SERVICES_BASE_URL + "/*");
    // // }
    //
    // // /**
    // // * @return
    // // */
    // // @Bean
    // // public IOrderDataProvider orderDataProvider() {
    // // return new OrderDataProvider();
    // // }
    //
    // /**
    // * @return
    // */
    // @Bean
    // protected SDAWebService2103Service opcBaseService() {
    // // Needed for correct ServiceName & WSDLLocation to publish contract
    // // first incl. original WSDL
    // return new SDAWebService2103Service();
    // }
    //
    // /**
    // * @return
    // */
    // @Bean
    // public SDAWebService2103 opcServiceProxy() {
    // return opcBaseService().getSDAWebService2103Port();
    // }
    //
    // @Bean("opcWrite")
    // public IOpcAction cartonReset() {
    // return new CartonDetectorReset();
    // }
    //
    // @Bean("opcRead")
    // public IOpcAction cartonSubscription() {
    // return new CartonSubscription();
    // }
    //
    // /**
    // * @return
    // */
    // @Bean
    // // @Scope("prototype")
    // public CompletableFuture<OpcUaClient> cartonSubscriptionFuture() {
    // CompletableFuture<OpcUaClient> future = new CompletableFuture<>();
    // future.whenComplete((client, ex) -> {
    // if (client != null) {
    // try {
    // client.disconnect().get();
    // Stack.releaseSharedResources();
    // } catch (InterruptedException | ExecutionException e) {
    // e.printStackTrace();
    // LOGGER.error("Error in disconnecting OPC client: ", e);
    // }
    // } else {
    // ex.printStackTrace();
    // LOGGER.error("Error during execution of async task for carton
    // subscription: ", ex);
    // Stack.releaseSharedResources();
    // }
    // });
    //
    // return future;
    // }
    //
    // /**
    // * @return
    // */
    // @Bean
    // // @Scope("prototype")
    // public CompletableFuture<OpcUaClient> cartonDetectorResetFuture() {
    // CompletableFuture<OpcUaClient> future = new CompletableFuture<>();
    // future = new CompletableFuture<>();
    // future.whenComplete((client, ex) -> {
    // if (client != null) {
    // try {
    // client.disconnect().get();
    // // Stack.releaseSharedResources();
    // } catch (InterruptedException | ExecutionException e) {
    // e.printStackTrace();
    // LOGGER.error("Error in disconnecting OPC client: ", e);
    // }
    // } else {
    // ex.printStackTrace();
    // LOGGER.error("Error during execution of async task for carton
    // subscription: ", ex);
    // // Stack.releaseSharedResources();
    // }
    // });
    //
    // return future;
    // }
    //
    // /**
    // * @return
    // */
    // @Bean
    // protected SAPInterface integrationService() {
    // return new SAPIntegrationEndpoint();
    // }
    //
    // /**
    // * @return
    // */
    // @Bean
    // protected SAPInterface_Service integrationBaseService() {
    // // Needed for correct ServiceName & WSDLLocation to publish contract
    // // first incl. original WSDL
    // return new SAPInterface_Service();
    // }
    //
    // /**
    // * @return
    // */
    // @Bean
    // public Endpoint integrationEndpoint() {
    // EndpointImpl endpoint = new EndpointImpl(springBus(),
    // integrationService());
    // // CXF JAX-WS implementation relies on the correct ServiceName as
    // // QName-Object with
    // // the name-Attribute´s text <wsdl:service name="Weather"> and the
    // // targetNamespace
    // // "http://www.codecentric.de/namespace/weatherservice/"
    // // Also the WSDLLocation must be set
    // // endpoint.setServiceName(new
    // endpoint.setServiceName(integrationBaseService().getServiceName());
    // endpoint.setWsdlLocation(integrationBaseService().getWSDLDocumentLocation().toString());
    // endpoint.publish(INTEGRATION_SERVICE_URL);
    //
    // return endpoint;
    // }
    //
    // @Bean
    // public ErpToDomainMapper erpToDomainMapper() {
    // return new ErpToDomainMapper();
    // }
}
