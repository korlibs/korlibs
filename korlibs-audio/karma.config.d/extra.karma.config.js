const fs = require('fs');
const path = require('path');

const basePath = config.basePath.replace(/\/node_modules$/, "");

config.set({
    basePath: basePath,
    files: [
        ...config.files,
        { pattern: 'kotlin/**/*', included: false, served: true, watched: false }
    ],
    middleware: [...(config.middleware || []), 'kotlinResources'],
    plugins: [
        ...(config.plugins || []),
        {
            'middleware:kotlinResources': ['factory', function () {
                return function (request, response, next) {
                    const urlPath = decodeURIComponent(request.url.split('?')[0]);
                    const relative = urlPath.replace(/^\//, ''); // strip only the leading slash
                    // real resources live under <basePath>/kotlin/..., matching
                    // what '/' -> '/base/kotlin/' proxy used to resolve to (but without recursion issue)
                    const filePath = path.join(basePath, 'kotlin', relative);

                    fs.readFile(filePath, (err, data) => {
                        if (err) return next();
                        response.writeHead(200);
                        response.end(data);
                    });
                };
            }]
        }
    ],
    client: {
        mocha: {
            timeout: 20000
        }
    }
});
