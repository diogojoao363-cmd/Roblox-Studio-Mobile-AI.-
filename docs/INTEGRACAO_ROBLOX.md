# Integração oficial com Roblox

## Objetivo
Conectar o Studio Mobile AI aos serviços oficiais do Roblox sem imitar login, coletar senhas ou contornar permissões.

## Acesso web já incluído
A versão atual abre https://create.roblox.com no navegador do Android pelo atalho "ABRIR CREATOR HUB". O login acontece no próprio site oficial, fora do aplicativo.

## Integrações planejadas
1. Abrir Creator Hub e Creator Store por links oficiais.
2. Importar/exportar projetos em formatos de place documentados pelo Roblox, quando houver um fluxo compatível e validado.
3. Conectar recursos de Open Cloud somente com autorização explícita do criador.
4. Publicar um place existente usando a API oficial e verificar o resultado da operação antes de informar sucesso.

## Segurança
- Nunca pedir a senha Roblox dentro do app.
- Nunca gravar cookies de sessão nem tokens em código, logs ou GitHub.
- Não compartilhar API keys no chat.
- Preferir OAuth oficial quando o fluxo e o escopo necessário estiverem disponíveis.
- Para chaves Open Cloud, usar armazenamento seguro no Android e permissões mínimas; não embutir chaves no APK.
- Toda publicação exige confirmação clara do usuário e validação de Universe ID, Place ID e permissões.

## Limites técnicos
Roblox Studio é um IDE oficial que inclui o motor Roblox. Um aplicativo Android independente não pode declarar que contém o mesmo motor ou todos os recursos do Studio sem uma integração licenciada/suportada. O editor móvel será desenvolvido por módulos e deverá preservar compatibilidade com formatos e APIs documentados. Alguns tipos de instância não são atualizados por certos fluxos de publicação Open Cloud; nesses casos será necessário o Studio oficial.

## Próximos módulos de engenharia
- Motor de cena 3D e seleção/transformação de objetos.
- Modelo de dados de cena compatível com as classes Roblox suportadas.
- Editor Luau com análise real, sem executar código arbitrário sem isolamento.
- Toolbox baseada em recursos autorizados do Creator Store.
- Assistente de IA que transforma pedidos em planos de edição revisáveis e ações concretas.
- Testes automatizados de importação/exportação e publicação.

## Referências oficiais
- https://create.roblox.com/docs
- https://create.roblox.com/docs/cloud
- https://create.roblox.com/docs/cloud/guides/usage-place-publishing
- https://create.roblox.com/docs/projects/place-files
