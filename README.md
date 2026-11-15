# Roblox Studio Mobile AI

Aplicativo Android em desenvolvimento, com interface inicial para organizar projetos Roblox e editar código Luau pelo celular.

## Estado atual — versão 0.1.0

- Interface Android nativa com Jetpack Compose.
- Navegação inicial entre Editor, Explorer e Propriedades.
- Campo de edição de texto Luau.
- Painel Output com mensagens de estado.
- Workflow GitHub Actions para compilar e disponibilizar o APK de depuração como artefato.

## Limitações desta primeira versão

Esta versão ainda não contém um motor 3D, execução de Luau, sincronização com Roblox Studio, importação/exportação de place ou publicação Roblox. Os painéis de cena e propriedades são apenas a estrutura inicial da interface; não representam uma edição real do mundo Roblox.

A integração futura deverá usar formatos e serviços oficiais da Roblox, com autenticação segura e permissões mínimas. Não coloque tokens, senhas ou chaves de API no código do aplicativo ou neste repositório.

## Como obter o APK de teste

1. Abra a aba **Actions** deste repositório.
2. Selecione **Android APK Build**.
3. Aguarde o workflow terminar com sucesso.
4. Abra a execução concluída e baixe o artefato **Roblox-Studio-Mobile-AI-debug**.

O APK gerado pelo workflow é uma compilação de depuração, não uma versão publicada na Play Store.
