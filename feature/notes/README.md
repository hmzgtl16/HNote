# `:feature:notes` Module

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :feature
    :feature:notes["notes"]
  end
  subgraph :core
    :core:data["data"]
    :core:ui["ui"]
    :core:navigation["navigation"]
  end

  :feature:notes --> :core:data
  :feature:notes --> :core:ui
  :feature:notes --> :core:navigation

classDef android-library fill:#3BD482,stroke:#fff,stroke-width:2px,color:#fff;
classDef unknown fill:#676767,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:notes android-library
class :core:data unknown
class :core:ui unknown
class :core:navigation unknown

```