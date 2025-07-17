# `:app` Module

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%
graph LR
    :app["app"]
    :benchmark["benchmark"]
    subgraph :feature
        :feature:notes["notes"]
        :feature:note["note"]
        :feature:search["search"]
        :feature:settings["settings"]
    end
    subgraph :core
        :core:data["data"]
        :core:design["design"]
        :core:navigation["navigation"]
    end

    :app --> :benchmark
    :app --> :feature:notes
    :app --> :feature:note
    :app --> :feature:search
    :app --> :feature:settings
    :app --> :core:data
    :app --> :core:design
    :app --> :core:navigation
    classDef android-application fill: #2C4162, stroke: #fff, stroke-width: 2px, color: #fff;
    classDef unknown fill: #676767, stroke: #fff, stroke-width: 2px, color: #fff;
    class :app android-application
    class :benchmark unknown
    class :feature:notes unknown
    class :feature:note unknown
    class :feature:search unknown
    class :feature:settings unknown
    class :core:data unknown
    class :core:design unknown
    class :core:navigation unknown

```