#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <fcntl.h>
#include <dlfcn.h>

#include <stdint.h>    /* C99 */

namespace art {
    namespace mirror {
        class Object {
        public:
            static constexpr size_t kVTableLength = 11;
            static uint32_t hash_code_seed;
            uint32_t klass_;
            uint32_t monitor_;
        };
        class Class : public Object {
        public:
            static constexpr size_t kImtSize = 64;
            uint32_t class_loader_;
            uint32_t component_type_;
            uint32_t dex_cache_;
            uint32_t dex_cache_strings_;
            uint32_t direct_methods_;
            uint32_t ifields_;
            uint32_t iftable_;
            uint32_t name_;
            uint32_t sfields_;
            uint32_t super_class_;
            uint32_t verify_error_class_;
            uint32_t virtual_methods_;
            uint32_t vtable_;
            uint32_t access_flags_;
            uint32_t class_size_;
            pid_t clinit_thread_id_;
            int32_t dex_class_def_idx_;
            int32_t dex_type_idx_;
            uint32_t num_reference_instance_fields_;
            uint32_t num_reference_static_fields_;
            uint32_t object_size_;
            uint32_t primitive_type_;
            uint32_t reference_instance_offsets_;
            uint32_t reference_static_offsets_;
            int32_t status_;
            static void *java_lang_Class_;
        };
        class ArtField : public Object {
        public:
            uint32_t declaring_class_;
            int32_t access_flags_;
            int32_t field_dex_idx_;
            int32_t offset_;
        };
        class ArtMethod : public Object {
        public:
            uint32_t declaring_class_;
            uint32_t dex_cache_resolved_methods_;
            uint32_t dex_cache_resolved_types_;
            uint32_t access_flags_;
            uint32_t dex_code_item_offset_;
            uint32_t dex_method_index_;
            uint32_t method_index_;
            struct PtrSizedFields {
                void *entry_point_from_interpreter_;
                void *entry_point_from_jni_;
                void *entry_point_from_quick_compiled_code_;
            } ptr_sized_fields_;
            static void *java_lang_reflect_ArtMethod_;
        };
    }
}
