package com.cmmplb.swagger.controller;

import io.swagger.models.*;
import io.swagger.models.Contact;
import io.swagger.models.Operation;
import io.swagger.models.Tag;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.service.*;
import springfox.documentation.swagger2.mappers.*;

import java.util.*;

@Primary
@Component
public class ServiceModelToSwagger2MapperA extends springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper {
    @Autowired
    private CompatibilityModelMapper compatibilityModelMapper;
    @Autowired
    private SecurityMapper securityMapper;
    @Autowired
    private LicenseMapper licenseMapper;
    @Autowired
    private VendorExtensionsMapper vendorExtensionsMapper;

    public ServiceModelToSwagger2MapperA() {
    }

    @BeforeMapping
    @SuppressWarnings("deprecation")
    void beforeMappingOperations(
            @MappingTarget Operation target,
            springfox.documentation.service.Operation source,
            @Context ModelNamesRegistry modelNamesRegistry) {
        List<io.swagger.models.parameters.Parameter> parameters = new ArrayList<>();
        if (useModelV3) {
            for (RequestParameter each : source.getRequestParameters()) {
                // parameters.addAll(Mappers.getMapper(RequestParameterMapper.class).mapParameter(each, modelNamesRegistry));
            }
            target.setResponses(mapResponses(source.getResponses(), modelNamesRegistry));
        } else {
            for (springfox.documentation.service.Parameter each : source.getParameters()) {
                parameters.add(Mappers.getMapper(ParameterMapper.class).mapParameter(each));
            }
            target.setResponses(mapResponseMessages(source.getResponseMessages()));
        }
        target.setParameters(parameters);
    }

    public Swagger mapDocumentation(Documentation from) {
        if (from == null) {
            return null;
        } else {
            Swagger swagger = new Swagger();
            // swagger.setVendorExtensions(this.vendorExtensionsMapper.mapExtensions(from.getVendorExtensions()));
            swagger.setSchemes(this.mapSchemes(from.getSchemes()));
            swagger.setPaths(this.mapApiListings(from.getApiListings()));
            swagger.setHost(from.getHost());
            swagger.setDefinitions(modelsFromApiListings(from.getApiListings()));
            // swagger.setSecurityDefinitions(this.securityMapper.toSecuritySchemeDefinitions(from.getResourceListing()));
            swagger.setInfo(this.mapApiInfo(this.fromResourceListingInfo(from)));
            swagger.setBasePath(from.getBasePath());
            swagger.setTags(this.tagSetToTagList(from.getTags()));
            List<String> list2 = from.getConsumes();
            if (list2 != null) {
                swagger.setConsumes(new ArrayList(list2));
            }

            List<String> list3 = from.getProduces();
            if (list3 != null) {
                swagger.setProduces(new ArrayList(list3));
            }

            return swagger;
        }
    }

    @Autowired
    @Value("${springfox.documentation.swagger.v2.use-model-v3:true}")
    private boolean useModelV3;

    Map<String, Model> modelsFromApiListings(Map<String, List<ApiListing>> apiListings) {
        if (useModelV3) {
            return Mappers.getMapper(ModelSpecificationMapper.class).modelsFromApiListings(apiListings);
        } else {
            Map<String, springfox.documentation.schema.Model> definitions = new TreeMap<>();
            apiListings.values().stream()
                    .flatMap(Collection::stream)
                    .forEachOrdered(each -> definitions.putAll(each.getModels()));
            return Mappers.getMapper(ModelMapper.class).mapModels(definitions);
        }
    }

    protected Info mapApiInfo(ApiInfo from) {
        if (from == null) {
            return null;
        } else {
            Info info = new Info();
            // info.setLicense(this.licenseMapper.apiInfoToLicense(from));
            // info.setVendorExtensions(this.vendorExtensionsMapper.mapExtensions(from.getVendorExtensions()));
            info.setTermsOfService(from.getTermsOfServiceUrl());
            info.setContact(this.map(from.getContact()));
            info.setVersion(from.getVersion());
            info.setTitle(from.getTitle());
            info.setDescription(from.getDescription());
            return info;
        }
    }

    protected Contact map(springfox.documentation.service.Contact from) {
        if (from == null) {
            return null;
        } else {
            Contact contact = new Contact();
            contact.setName(from.getName());
            contact.setUrl(from.getUrl());
            contact.setEmail(from.getEmail());
            return contact;
        }
    }

    protected Operation mapOperation(springfox.documentation.service.Operation from, ModelNamesRegistry modelNames) {
        if (from == null) {
            return null;
        } else {
            Operation operation = new Operation();
            // this.beforeMappingOperations(operation, from, modelNames);
            // operation.vendorExtensions(this.vendorExtensionsMapper.mapExtensions(from.getVendorExtensions()));
            operation.setDescription(from.getNotes());
            operation.setSchemes(this.stringSetToSchemeList(from.getProtocol(), modelNames));
            operation.setSecurity(this.mapAuthorizations(from.getSecurityReferences()));
            operation.setOperationId(from.getUniqueId());
            operation.setSummary(from.getSummary());
            Set<String> set = from.getConsumes();
            if (set != null) {
                operation.setConsumes(new ArrayList(set));
            }

            Set<String> set1 = from.getProduces();
            if (set1 != null) {
                operation.setProduces(new ArrayList(set1));
            }

            Set<String> set2 = from.getTags();
            if (set2 != null) {
                operation.setTags(new ArrayList(set2));
            }

            if (from.getDeprecated() != null) {
                operation.setDeprecated(Boolean.parseBoolean(from.getDeprecated()));
            }

            return operation;
        }
    }

    protected Tag mapTag(springfox.documentation.service.Tag from) {
        if (from == null) {
            return null;
        } else {
            Tag tag = new Tag();
            // tag.setVendorExtensions(this.vendorExtensionsMapper.mapExtensions(from.getVendorExtensions()));
            tag.setName(from.getName());
            tag.setDescription(from.getDescription());
            return tag;
        }
    }

    private ApiInfo fromResourceListingInfo(Documentation documentation) {
        if (documentation == null) {
            return null;
        } else {
            ResourceListing resourceListing = documentation.getResourceListing();
            if (resourceListing == null) {
                return null;
            } else {
                ApiInfo info = resourceListing.getInfo();
                return info == null ? null : info;
            }
        }
    }

    protected List<Tag> tagSetToTagList(Set<springfox.documentation.service.Tag> set) {
        if (set == null) {
            return null;
        } else {
            List<Tag> list = new ArrayList(set.size());
            Iterator var3 = set.iterator();

            while(var3.hasNext()) {
                springfox.documentation.service.Tag tag = (springfox.documentation.service.Tag)var3.next();
                list.add(this.mapTag(tag));
            }

            return list;
        }
    }

    protected List<Scheme> stringSetToSchemeList(Set<String> set, ModelNamesRegistry modelNames) {
        if (set == null) {
            return null;
        } else {
            List<Scheme> list = new ArrayList(set.size());
            Iterator var4 = set.iterator();

            while(var4.hasNext()) {
                String string = (String)var4.next();
                list.add((Scheme)Enum.valueOf(Scheme.class, string));
            }

            return list;
        }
    }
}