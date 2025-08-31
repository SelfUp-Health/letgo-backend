package schema

def version = "${project.version}".tokenize('-')[0]

def environment = properties.env != null ? properties.env.replaceAll('"', '') : null

def flywayDirectory = "${project.basedir}/src/main/resources/db/schema/${version}"

// get migration name from stdin if null
def name = properties.name ?: (System.console().readLine('Type name for your migration: \n'))

name = name.trim().toLowerCase().replaceAll(' ', '_')

def timestamp = (long) ((new Date()).getTime()) / 1000

migrationName = "V${version}.${timestamp}__${name}.sql"

def directory = new File("${flywayDirectory}")
// create directory if does not exist
if (!directory.exists()) {
  log.info("Migration file directory for version ${version} doesn't exist. Creating..")
  directory.mkdirs()
}

log.info("Creating new migration file..")
def created = new File("${flywayDirectory}/${migrationName}").createNewFile()

if (created) {
  log.info("${migrationName} migration file created.")
} else {
  log.error("Migration file couldn't be created.")
  return 1
}
