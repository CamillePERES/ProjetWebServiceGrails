// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'projetapi.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'projetapi.UserRole'
grails.plugin.springsecurity.authority.className = 'projetapi.Role'

grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"

grails.plugin.springsecurity.interceptUrlMap = [
        [pattern: '/api/login',      access: ['permitAll']],
        [pattern: '/api/logout',     access: ['isFullyAuthenticated()']],
        [pattern: '/**',             access: ['isFullyAuthenticated()']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/api/', filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],
        //[pattern: '/**', filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
]

grails.plugin.springsecurity.rest.token.storage.jwt.secret = 'qrD6h8K6S9503Q06Y6Rfk21TErImPYqa'
grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'
grails.plugin.springsecurity.rest.token.validation.useBearerToken = true