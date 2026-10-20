# Auditoria de compatibilidade: HQ Speaker 1.0.9

Data: 2026-10-19. Minecraft 1.21.1, NeoForge 21.1.236, Java 21,
Sable 2.0.5 e CC:Tweaked 1.120.0.

## Resultado e alteração

O suporte à 1.0.9 foi declarado sem modificar Java, mixins ou a ponte Sable.
A dependência de desenvolvimento passou para o JAR 1.0.9 fornecido pelo usuário.
A faixa gerada no manifesto é:

```text
[1.0.4,1.0.5),[1.0.7,1.0.10),[1.1.4-neoforge-1.21.1,1.1.5)
```

A versão do addon é 0.3.0. O suporte às versões anteriores do HQ Speaker
foi preservado.

## Comparação dos artefatos

| Artefato | SHA-256 |
| --- | --- |
| HQ Speaker 1.0.8 | `D4637A4FF30183315F0AA04F1E5E99ECCF078D5E780F9BE00FF12A2EBD82325B` |
| HQ Speaker 1.0.9 | `24FD04A9B60FAB92BC38DF940D2A898C09E0FAE8A4123069F36B242D66E54B4E` |

Ambos os JARs possuem 89 entradas e 62 classes. Não houve adição ou remoção de
entradas. As diferenças se limitam aos metadados de versão e a
`HQSpeakerPeripheral`, `HQSpeakerPeripheral$1` e `HQSpeakerPeripheral$SyncDispatch`.
Nas duas classes internas, assinaturas e instruções disassembladas são iguais.

As assinaturas públicas/protegidas do periférico e as assinaturas/anotações das
66 funções Lua permanecem iguais. As classes de áudio do cliente, os pacotes,
a configuração e os demais recursos são idênticos à 1.0.8.

As alterações funcionais privadas do periférico são:

- Contagem de conexões de cada speaker por computador em `attach`/`detach`.
- Busca recursiva do ambiente através de campos `IComputerAccess`, incluindo o
  encapsulamento dos modems cabeados; limite de profundidade 8.
- Remoção de conexões inválidas quando o envio de eventos de disponibilidade ou
  metadados ICY lança uma exceção de execução.

`computeWorldPos`, `currentLevel`, `currentBlockPos`, `clampVol`,
`clampVolChecked`, `radiusSq`, `sendToNearby` e `sendAudioChunkedToNearby`
mantêm descritores e instruções equivalentes, desconsiderando índices da
tabela de constantes. O campo `speakerDefaultVolume` também foi preservado.
Nenhum ponto acessado pelo addon exige adaptação.

## Evidências da implementação

| Verificação | Resultado |
| --- | --- |
| `scripts/gradle.ps1 build --offline --console=plain` | Passou com Java 21; sem fontes de teste no projeto |
| Manifesto dentro do JAR gerado | Faixa acima confirmada; versão 0.3.0 |
| Interpretação da faixa com Maven `VersionRange` | Aceita 1.0.4, 1.0.7, 1.0.8, 1.0.9 e 1.1.4-neoforge-1.21.1; rejeita 1.0.3, 1.0.5, 1.0.6, 1.0.10 e 1.1.5 |
| Conteúdo do JAR | Oito classes do addon e configuração dos mixins; sem classes de HQ Speaker/Sable ou dependências incorporadas |
| Servidor dedicado | Antes do incremento de versão, iniciou até `Done` com HQ Speaker 1.0.9, addon 0.2.0 e Sable 2.0.5; não repetido na 0.3.0 |
| Mixins comuns no servidor | Cinco mixins aplicados, sem falha de aplicação registrada |
| Bytecode exportado do servidor | Chamada à projeção Sable em `computeWorldPos` e redirecionamento de `maxVolume()` nas duas rotinas de volume confirmados |
| Cliente | Carregou os mods e o manipulador de áudio; tentativa de conexão recusada porque o servidor de teste não estava disponível |
| Bytecode exportado do cliente | Projeção Sable inserida nas duas sobrecargas de `resolvePacketWorldPos` |
| GameTests | Não foram criados nem executados, conforme instrução do usuário |

O mixin de `HQSpeakerSound` não foi observado em classe carregada: a reprodução
não chegou a ser exercitada. Seus alvos permanecem idênticos no JAR original.
Os ajustes opcionais de constantes antigas podem ficar sem aplicação nas
versões novas; isso é esperado e não equivale a falha de transformação.

O servidor mínimo registrou um erro de propriedade física do Sable para
`create:flywheel`, ausente dessa instalação isolada. Isso não impediu a
inicialização; não foi alterado nenhum código para contornar esse aviso.
As tentativas auxiliares de reinício fora do Gradle também encontraram erros
de classpath no lançador; não constituem validação adicional do servidor.
A inspeção visual foi indisponível porque Computer Use não autorizou controlar
a janela Java. As instâncias criadas para a validação foram encerradas.

Os logs e exports locais ficam em `build/validation/109-server/` e
`build/validation/109-client/`. São artefatos ignorados pelo Git.

## Verificações funcionais pendentes

Não há confirmação de reprodução, percepção auditiva, alcance ou movimento.
Permanecem pendentes a conexão efetiva cliente/servidor, reprodução direta e
via modem, arquivos fragmentados, streaming, controles/canais, volumes 1/3/16,
movimento/rotação de subnível, desconexão/reconexão de caminhos simultâneos e
regressão em jogo com 1.0.7, 1.0.8 e 1.1.4-neoforge-1.21.1.

Esses resultados não devem ser inferidos do build ou da aplicação dos mixins.
