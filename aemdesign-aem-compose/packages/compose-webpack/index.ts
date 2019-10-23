import * as config from './lib/config'
import runtime from './lib/runtime'
import * as loaders from './loaders'
import * as plugins from './plugins'

const modules = { config, loaders, plugins, runtime }

export { config, loaders, plugins, runtime }

export default modules
