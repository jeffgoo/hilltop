package hilltop

import hilltop.cli.Cli
import hilltop.commands.*

class App {
  def config = new ConfigLoader().load()

  def App(String... args) {

    new Cli('hilltop', {
      describe 'An Anthill command-line utility'
      options {
        v longOpt: 'version', 'Gets the current Hilltop version'
      }
      execute { params ->
        if (params.v) {
          println 'hilltop version: 0.1'; System.exit(0)
        }
      }

      command('config') {
        def handler = new ConfigCommands(config)
        describe 'Manage configuration values'

        command('get') {
          describe 'Get a configuration value'
          arguments exactly: 1
          execute { p -> handler.get(p.arguments().first()) }
        }

        command('set') {
          describe 'Set configuration values; <property=value> ...'
          arguments minimum: 1
          execute { p -> handler.set(p.arguments()) }
        }

        command('remove') {
          describe 'Remove configuration values'
          arguments minimum: 1
          execute { p -> handler.remove(p.arguments()) }
        }

        command('list') {
          describe 'List the current configuration values'
          execute { handler.list() }
        }
      }

      command('project') {
        def handler = new ProjectCommands(config)
        describe 'Working with Anthill projects'

        command('list') {
          describe 'List Anthill projects'
          options {
            f longOpt: 'folder', args: 1, 'List Anthill projects in a specific folder'
            i longOpt: 'inactive', 'Includes inactive projects'
          }
          execute { params ->
            if (params.f) handler.list_folder(params.f, params.i)
            else handler.list(params.i)
          }
        }

        command('show') {
          describe 'Show details of an Anthill project'
          arguments exactly: 1, name: 'project'
          execute { p ->
            handler.show(p.arguments().first())
          }
        }

        command('open') {
          describe 'Launch an Anthill project in the browser'
          arguments exactly: 1, name: 'project'
          options {
            a longOpt: 'admin', 'Launch the administrative configuration page'
          }
          execute { p ->
            handler.open(p.arguments().first(), p.a)
          }
        }
      }

      command('workflow') {
        def handler = new WorkflowCommands(config)
        describe 'Working with Anthill workflows'

        command('show') {
          describe 'Show details of an Anthill workflow'
          arguments exactly: 2, name1: 'project', name2: 'workflow'
          execute { p ->
            handler.show(p.arguments()[0], p.arguments()[1])
          }
        }

        command('open') {
          describe 'Launch an Anthill workflow in the browser'
          arguments exactly: 2, name1: 'project', name2: 'workflow'
          options {
            a longOpt: 'admin', 'Launch the administrative configuration page'
          }
          execute { p ->
            handler.open(p.arguments()[0], p.arguments()[1], p.a)
          }
        }
      }

      command('build') {
        def handler = new BuildCommands(config)
        describe 'Working with Anthill builds'

        command('request') {
          describe 'Request a new Anthill buildlife'
          arguments exactly: 2, name1: 'project', name2: 'workflow'
          execute { p ->
            handler.request(p.arguments()[0], p.arguments()[1])
          }
        }

        command('run') {
          describe 'Run a secondary workflow against an Anthill buildlife'
          arguments exactly: 2, name1: 'buildlife', name2: 'workflow'
          options { s longOpt: 'servergroup', 'The environment to run the workflow in' }
          execute { p ->
            handler.run(p.arguments()[0], p.arguments()[1], p.s)
          }
        }

        command('open') {
          describe 'Launch an Anthill buildlife in the browser'
          arguments exactly: 1, name: 'buildlife'
          execute { p -> handler.open(p.arguments()[0]) }
        }
      }
    }).run(args)
  }
}
