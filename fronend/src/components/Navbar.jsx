import {CirclePlus} from "lucide-react";
const Navbar = ({username}) => {
    return (
        <div className="bg-neutral-800 fixed top-0 left-0 w-full h-14 flex justify-between items-center px-4 text-white z-50">
            <h1 className="text-xl font-semibold text-amber-500">Project WEBAPI</h1>
            <p className="text-white text-sm">Connected as : { username }</p>
            <div className="flex cursor-pointer hover:bg-amber-600 p-2 rounded-full duration-300">
                <CirclePlus />
            </div>
        </div>
    );
};

export default Navbar;