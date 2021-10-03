// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'projetapigrails.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'projetapigrails.UserRole'
grails.plugin.springsecurity.authority.className = 'projetapigrails.Role'
grails.plugins.springsecurity.securityConfigType = "Annotation"

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/api/**',
         filters: 'JOINED_FILTERS, -exceptionTranslationFilter,' +
                 '-authenticationProcessingFilter,-securityContextPersistenceFilter,' +
                 '-rememberMeAuthenticationFilter'],
       // [pattern: '/**', filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
]

grails.plugin.springsecurity.rest.token.storage.jwt.secret = 'qrD6h8K6S9503Q06Y6Rfk21TErImPYqa'
grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'
grails.plugin.springsecurity.rest.token.validation.useBearerToken = true

