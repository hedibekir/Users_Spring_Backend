import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property


plugins {
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	id ("org.flywaydb.flyway") version "9.18.0"
	id ("nu.studer.jooq") version "8.2"
	kotlin("jvm") version "1.8.21"
	kotlin("plugin.spring") version "1.8.21"
	kotlin("plugin.jpa") version "1.8.21"
}

group = "com.expatrio"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("jakarta.validation:jakarta.validation-api")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	jooqGenerator("org.postgresql:postgresql:42.5.4")
}

flyway {
	url = "jdbc:postgresql://localhost:5432/expatrio"
	user = "expatrio"
	password = "expatrio"
}

jooq {
	configurations {
		create("main") {
			jooqConfiguration.apply {
				logging = Logging.WARN
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = "jdbc:postgresql://localhost:5432/expatrio"
					user = "expatrio"
					password = "expatrio"
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "public"
						excludes = "FLYWAY_SCHEMA_HISTORY"
						forcedTypes = listOf(
							ForcedType().apply {
								userType = "com.expatrio.api.model.UserRole"
								isEnumConverter = true
								objectType = org.jooq.meta.jaxb.ForcedTypeObjectType.COLUMN
								includeExpression = ".*\\.role"
							}
						)
					}
					generate.apply {
						isRelations = true
						isDeprecated = false
						isRecords = true
						isDaos = false
						isPojos = false
						isImmutablePojos = false
						isFluentSetters = true
					}
					target.apply {
						packageName = "com.expatrio.api"
						directory = "src/main/generated/java"
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}

tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") {
	dependsOn("flywayMigrate")

	// declare Flyway migration scripts as inputs on the jOOQ task
	inputs.files(fileTree("src/main/resources/db/migration"))
		.withPropertyName("migrations")
		.withPathSensitivity(PathSensitivity.RELATIVE)
	allInputsDeclared.set(true)
}




tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}


