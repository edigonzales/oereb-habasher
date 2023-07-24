# oereb-habasher


## Run

```
jbang oereb_habasher.java
```

Without jbang installed:

```
curl -Ls https://sh.jbang.dev | bash -s - oereb_habasher.java
```

## Develop

```
jbang edit oereb_habasher.java
```

Auf Apple Silicon ist vscodium noch nicht verfügbar. Aus diesem Grund muss als Editor VS Code verwendet werden. Damit der Aufruf via Console funktioniert, muss in .zshrc der PATH angepasst werden:

```
export PATH="$PATH:/Users/stefan/apps/vscode/Visual Studio Code.app/Contents/Resources/app/bin"
```

```
jbang edit --open=code create_schema_sql.java

```