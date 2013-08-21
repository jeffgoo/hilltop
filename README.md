# hilltop

Command-line utility for AnthillPro, a deploy, test, and release automation framework.

## setup

* JRE 1.6+
* Groovy 2.1+

    `brew/chocolatey install groovy`

* Gradle 1.6+

    `brew/chocolatey install gradle`

* Unzip the [Anthill3 Dev Kit](http://docs.urbancode.com/anthill3-help-3.8/html/DevKit.html) into the *./depends* folder
* Build the dependencies jar `gradle assemble`
* Run the tests `gradle test`
* Add configuration variables
    * anthill server `./hilltop config set anthill.api_server=anthill.local`
    * authorization token `./hilltop config set anthill.api_token=mytoken`

## commands

Config

    ./hilltop config list
    ./hilltop config set anthill.api_server=anthill.local
    ./hilltop config get anthill.api_server

Projects

    ./hilltop project list
    ./hilltop project list --folder Services
    ./hilltop project show myproject
    ./hilltop project open myproject

Workflows

    ./hilltop workflow show myproject myworkflow
    ./hilltop workflow open myproject myworkflow

Builds

    ./hilltop build request myproject myworkflow
    ./hilltop build open mybuildlifeid
    ./hilltop build run --servergroup "Candidate Integration" mybuildlifeid myworkflow

## contributing

1. Fork it
2. Create your feature branch `git checkout -b my-new-feature`
3. Commit your changes `git commit -am 'Added some feature'`
4. Push to the branch `git push origin my-new-feature`
5. Create new Pull Request
